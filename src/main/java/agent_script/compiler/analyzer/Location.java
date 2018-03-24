package agent_script.compiler.analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Represents an exact location within a source file.
 */
public final class Location {
    /**
     * The number of lines to include before and after the exact location in the annotated source.
     */
    private static final int ANNOTATION_RADIUS = 3;

    /**
     * The line number separator string in the annotated source.
     */
    private static final String LINE_NUMBER_SEPARATOR = "|  ";

    /**
     * The source path.
     */
    private final Path sourcePath;

    /**
     * The row index.
     */
    private final int rowIndex;

    /**
     * The column index.
     */
    private final int columnIndex;

    public Location(Path sourcePath, int rowIndex, int columnIndex) {
        this.sourcePath = sourcePath;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    /**
     * Formats a line number to the specified width.
     *
     * @param lineNumber       the line number
     * @param lineNumberLength the formatted length of the line number
     * @return the formatted line number
     */
    private String formatLineNumber(int lineNumber, int lineNumberLength) {
        return String.format("%-" + lineNumberLength + "d", lineNumber);
    }

    /**
     * @return the value of {@link #sourcePath}
     */
    public Path getSourcePath() {
        return sourcePath;
    }

    /**
     * @return the value of {@link #rowIndex}
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * @return the value of {@link #columnIndex}
     */
    public int getColumnIndex() {
        return columnIndex;
    }

    /**
     * @return the location as a one-liner description
     */
    public String toDescription() {
        return String.format("%s:%d:%d", sourcePath.toAbsolutePath().toString(), rowIndex, columnIndex);
    }

    /**
     * @return a portion of the source code annotated with a marker at the exact location
     */
    public String toAnnotatedSource() {
        List<String> lines;
        try {
            // TODO not the most efficient solution - better use Files.lines
            lines = Files.readAllLines(sourcePath);
        } catch (IOException e) {
            return "<NO SOURCE AVAILABLE>";
        }

        int actualRowIndex = rowIndex - 1;

        StringBuilder builder = new StringBuilder();

        boolean startLinesDropped = true;
        int startRowIndex = actualRowIndex - ANNOTATION_RADIUS;
        if (startRowIndex < 0) {
            startRowIndex = 0;
            startLinesDropped = false;
        }

        boolean endLinesDropped = true;
        int endRowIndex = actualRowIndex + ANNOTATION_RADIUS;
        if (endRowIndex > lines.size() - 1) {
            endRowIndex = lines.size() - 1;
            endLinesDropped = false;
        }

        int lineNumberLength = Integer.toString(endRowIndex).length() + 1;

        // Before
        if (startLinesDropped) {
            builder.append("{ ... }\n");
        }

        for (int i = startRowIndex; i <= actualRowIndex; i++) {
            builder
                    .append(formatLineNumber(i + 1, lineNumberLength))
                    .append(LINE_NUMBER_SEPARATOR)
                    .append(lines.get(i))
                    .append('\n');
        }

        // Annotation
        for (int i = 0; i < columnIndex + lineNumberLength + LINE_NUMBER_SEPARATOR.length(); i++) {
            builder.append(' ');
        }

        builder.append("^\n");

        // After
        for (int i = actualRowIndex + 1; i < endRowIndex; i++) {
            builder
                    .append(formatLineNumber(i + 1, lineNumberLength))
                    .append(LINE_NUMBER_SEPARATOR)
                    .append(lines.get(i))
                    .append('\n');
        }

        if (endLinesDropped) {
            builder.append("{ ... }");
        }

        return builder.toString();
    }
}
