package utils;

import android.widget.EditText;

import us.feras.mdv.MarkdownView;


public class Document {
    String name;
    String body;

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Document() {}

    public void setHeader(int line, MarkdownView markdownView, EditText editor) {
        //get current line text
        String text = editor.getText().toString();
        int lineStart = editor.getLayout().getLineStart(line);
        int end = editor.getLayout().getLineEnd(line);
        String lineText = text.substring(lineStart, end);

        String frontText = notSelectedFrontTextHandler(editor, lineStart);
        String behindText = notSelectedBehindTextHandler(editor, end);

        if (lineText.charAt(0) == '>' || lineText.charAt(0) == '*') {
            return;
        }
        //handle different heading
        if (lineText.charAt(0) != '#') {
            editor.setText(frontText + "# " + lineText + behindText);
        } else if (lineText.charAt(1) != '#' || lineText.charAt(2) != '#') {
            editor.setText(frontText + "#" + lineText + behindText);
        } else if (lineText.charAt(2) == '#') {
            editor.setText(frontText + lineText.substring(4, lineText.length()) + behindText);
        }
    }

    public void setBold(int line, EditText editor, MarkdownView markdownView) {
        int start_selected = editor.getSelectionStart();
        int end_selected = editor.getSelectionEnd();

        if (start_selected != end_selected) {

            String text = editor.getText().toString();

            int lineStart = editor.getLayout().getLineStart(line);
            int end = editor.getLayout().getLineEnd(line);
            String lineText = text.substring(lineStart, end);
            String frontText = notSelectedFrontTextHandler(editor, start_selected);
            String behindText = notSelectedBehindTextHandler(editor, end_selected);
            String selectedText = text.substring(start_selected, end_selected);
            //handle different heading
            if (lineText.charAt(0) == '#' || lineText.charAt(0) == '>' || lineText.charAt(0) == '*') {
                return;
            }
            if (selectedText.charAt(0) != '_') {
                selectedText.replace('_', ' ');
                editor.setText(frontText + "_" + selectedText + "_" + behindText);
            } else if (selectedText.charAt(1) != '_') {
                selectedText.substring(1, selectedText.length() - 1).replace('_', ' ');
                editor.setText(frontText + "__" + selectedText + "__" + behindText);
            } else {
                String s = selectedText.substring(2, selectedText.length() - 2).replace('_', ' ');

                editor.setText(frontText + s + behindText);
            }

        }
    }



    public void setQuote(int line, EditText editor, MarkdownView markdownView) {
        //get current line text
        String text = editor.getText().toString();
        int lineStart = editor.getLayout().getLineStart(line);
        int end = editor.getLayout().getLineEnd(line);
        String lineText = text.substring(lineStart, end);
        String frontText = notSelectedFrontTextHandler(editor, lineStart);
        String behindText = notSelectedBehindTextHandler(editor, end);

        if (lineText.charAt(0) == '#' || lineText.charAt(0) == '*') {
            return;
        }

        if (lineText.charAt(0) == '*') {
            return;
        }

        //handle different heading
        if (lineText.charAt(0) != '>') {
            editor.setText(frontText + "> " + lineText + behindText);
        } else {

            editor.setText(frontText + lineText.substring(2, lineText.length()) + behindText);
        }
    }

    public void setList(int line, EditText editor, MarkdownView markdownView) {
        //get current line text
        String text = editor.getText().toString();
        int lineStart = editor.getLayout().getLineStart(line);
        int end = editor.getLayout().getLineEnd(line);
        String lineText = text.substring(lineStart, end);
        String frontText = notSelectedFrontTextHandler(editor, lineStart);
        String behindText = notSelectedBehindTextHandler(editor, end);
        if (lineText.charAt(0) == '#' || lineText.charAt(0) == '>') {
            return;
        }

        if (lineText.charAt(0) == '>') {
            return;
        }

        //handle different heading
        if (lineText.charAt(0) != '*') {
            editor.setText(frontText + "* " + lineText + behindText);
        } else {

            editor.setText(frontText + lineText.substring(2, lineText.length()) + behindText);
        }
    }


    public String notSelectedFrontTextHandler(EditText editor, int start) {

        String text = editor.getText().toString();
        String frontText;
        if (start != 0) {
            frontText = text.substring(0, start);
        } else {
            frontText = "";
        }
        return frontText;
    }

    public String notSelectedBehindTextHandler(EditText editor, int end) {
        String text = editor.getText().toString();
        String behindText;
        if (end < editor.length()) {
            behindText = text.substring(end, editor.length());
        } else {
            behindText = "";

        }

        return behindText;
    }

}
