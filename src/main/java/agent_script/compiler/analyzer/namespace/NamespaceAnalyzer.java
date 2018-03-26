package agent_script.compiler.analyzer.namespace;

import agent_script.antlr.AgentScriptBaseVisitor;
import agent_script.antlr.AgentScriptParser;
import agent_script.compiler.Compiler;
import agent_script.compiler.*;
import agent_script.compiler.analyzer.Location;
import agent_script.compiler.analyzer.Symbol;
import org.antlr.v4.runtime.Token;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static agent_script.compiler.CompilerMessageTemplates.*;

/**
 * Analyzes the parse tree and produces a namespace definition.
 */
public final class NamespaceAnalyzer extends CompilerProcessor implements ICompilerProcessor {
    /**
     * The set of imported namespace names.
     */
    private Set<Symbol> namespaceNames;

    /**
     * The produced namespace definition.
     */
    private NamespaceDefinition namespaceDefinition;

    public NamespaceAnalyzer(CompilerMessageReporter messageReporter) {
        super(messageReporter);
    }

    @Override
    public void process(NamespaceBundle namespaceBundle) throws CompilerException {
        this.namespaceBundle = namespaceBundle;
        namespaceNames = new HashSet<>();
        new Visitor().visit(namespaceBundle.getParseTree());
    }

    /**
     * The visitor class which traverses the parse tree.
     */
    private class Visitor extends AgentScriptBaseVisitor<Void> {
        /**
         * The set of known meta symbols.
         */
        private /*static*/ final Set<Symbol> KNOWN_META_SYMBOLS =
                Arrays.stream(new String[]{"private", "native", "macro"})
                        .map(Symbol::asNameSymbol)
                        .collect(Collectors.toSet());

        /**
         * @param namespaceName the name of the namespace
         * @return whether the name of the namespace matches the source path
         */
        private boolean namespaceNameMatchesSourcePath(Symbol namespaceName) {
            Path relativeSourcePath = namespaceBundle.getRootSourcePath().relativize(namespaceBundle.getSourcePath());
            String[] namespaceParts = namespaceName.getNamespaceFragments();
            int sourcePathNameCount = relativeSourcePath.getNameCount();
            if (sourcePathNameCount != namespaceParts.length) {
                return false;
            }

            int lastIndex = sourcePathNameCount - 1;
            for (int i = 0; i < lastIndex; i++) {
                if (!relativeSourcePath.getName(i).toString().equals(namespaceParts[i])) {
                    return false;
                }
            }

            return relativeSourcePath.getName(lastIndex).toString().equals(namespaceParts[lastIndex] + Compiler.SCRIPT_FILE_EXTENSION);
        }

        @Override
        public Void visitMeta(AgentScriptParser.MetaContext ctx) {
            Map<Symbol, List<Token>> groupedMetaSymbols = ctx.metaSymbols.stream()
                    .collect(Collectors.groupingBy(metaSymbol -> Symbol.asNameSymbol(metaSymbol.getText())));

            groupedMetaSymbols.forEach((metaName, metaSymbols) -> {
                if (metaSymbols.size() > 1) {
                    reportMessage(A_0000.render(getLocation(metaSymbols.get(0)), metaName));
                }
            });

            groupedMetaSymbols.forEach((metaName, metaSymbols) -> {
                if (!KNOWN_META_SYMBOLS.contains(metaName)) {
                    metaSymbols.forEach(metaSymbol -> reportMessage(A_0001.render(getLocation(metaSymbol), metaName)));
                }
            });

            return null;
        }

        @Override
        public Void visitNamespaceDefinition(AgentScriptParser.NamespaceDefinitionContext ctx) {
            Location location = getLocation(ctx.nameSymbol);
            String namespaceNameText = ctx.nameSymbol.getText();
            Symbol namespaceName = Symbol.asNamespaceSymbol(namespaceNameText);
            if (namespaceName == null) {
                reportMessage(A_0002.render(location, namespaceNameText));
                throw new CompilerException();
            }

            if (!namespaceNameMatchesSourcePath(namespaceName)) {
                reportMessage(A_0003.render(location, namespaceName, namespaceBundle.getSourcePath()));
                throw new CompilerException();
            }

            namespaceDefinition = new NamespaceDefinition(namespaceName, location);
            namespaceBundle.setNamespaceDefinition(namespaceDefinition);
            namespaceNames.add(namespaceName);

            if (ctx.meta() != null) {
                namespaceDefinition.getMeta().addAll(
                        ctx.meta().metaSymbols
                                .stream()
                                .map(Token::getText)
                                .map(Symbol::asNameSymbol)
                                .collect(Collectors.toList()));
            }

            return null;
        }

        @Override
        public Void visitImportDeclaration(AgentScriptParser.ImportDeclarationContext ctx) {
            Location location = getLocation(ctx.nameSymbol);
            String importedNamespaceNameText = ctx.nameSymbol.getText();
            Symbol importedNamespaceName = Symbol.asNamespaceSymbol(importedNamespaceNameText);
            if (importedNamespaceName == null) {
                reportMessage(A_0002.render(location, importedNamespaceNameText));
                return null;
            }

            if (importedNamespaceName.equals(namespaceDefinition.getName())) {
                reportMessage(A_0004.render(location, importedNamespaceName));
                return null;
            }

            Map<Symbol, Symbol> importedNamespaceNames = namespaceDefinition.getImportedNamespaceNames();

            if (ctx.aliasSymbol != null) {
                Location aliasLocation = getLocation(ctx.aliasSymbol);

                String importedNamespaceAliasText = ctx.aliasSymbol.getText();
                Symbol importedNamespaceAlias = Symbol.asNamespaceSymbol(importedNamespaceAliasText);
                if (importedNamespaceAlias == null) {
                    reportMessage(A_0005.render(aliasLocation, importedNamespaceAliasText));
                    return null;
                }

                if (!NamespaceAnalyzer.this.namespaceNames.add(importedNamespaceAlias)) {
                    reportMessage(A_0006.render(aliasLocation, importedNamespaceAlias));
                    return null;
                }

                importedNamespaceNames.put(importedNamespaceAlias, importedNamespaceName);
            }

            if (!NamespaceAnalyzer.this.namespaceNames.add(importedNamespaceName)) {
                reportMessage(A_0007.render(location, importedNamespaceName));
                return null;
            }

            importedNamespaceNames.put(importedNamespaceName, importedNamespaceName);
            return null;
        }

        @Override
        public Void visitConstantDefinition(AgentScriptParser.ConstantDefinitionContext ctx) {
            Location location = getLocation(ctx.nameSymbol);
            String constantNameText = ctx.nameSymbol.getText();
            Symbol constantName = Symbol.asNameSymbol(constantNameText);
            if (constantName == null) {
                reportMessage(A_0008.render(location, constantNameText));
                return null;
            }

            ConstantDefinition constantDefinition = new ConstantDefinition(qualifySymbol(constantName), location);

            if (ctx.meta() != null) {
                constantDefinition.getMeta().addAll(
                        ctx.meta().metaSymbols
                                .stream()
                                .map(Token::getText)
                                .map(Symbol::asNameSymbol)
                                .collect(Collectors.toList()));
            }

            if (namespaceDefinition.getConstantDefinitions().put(constantName, constantDefinition) != null) {
                reportMessage(A_0009.render(location, constantName));
            }

            return visitChildren(ctx);
        }

        @Override
        public Void visitFunctionDefinition(AgentScriptParser.FunctionDefinitionContext ctx) {
            Location location = getLocation(ctx.nameSymbol);
            String functionNameText = ctx.nameSymbol.getText();
            Symbol functionName = Symbol.asNameSymbol(functionNameText);
            if (functionName == null) {
                reportMessage(A_0010.render(location, functionNameText));
                return null;
            }

            FunctionDefinition functionDefinition = new FunctionDefinition(qualifySymbol(functionName), location);
            functionDefinition.setArity(ctx.argumentSymbols.size());

            if (ctx.meta() != null) {
                functionDefinition.getMeta().addAll(
                        ctx.meta().metaSymbols
                                .stream()
                                .map(Token::getText)
                                .map(Symbol::asNameSymbol)
                                .collect(Collectors.toList()));
            }

            if (namespaceDefinition.getFunctionDefinitions().put(functionName, functionDefinition) != null) {
                reportMessage(A_0011.render(location, functionName));
                throw new CompilerException();
            }

            return visitChildren(ctx);
        }
    }
}
