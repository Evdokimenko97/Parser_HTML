public class ElementCategories {
    private int tag;
    private int type;
    private int cursor;
    private int border;
    private int placeholder;
    private String ownText;

    public ElementCategories() {

    }

    public ElementCategories(int tag, int type, int cursor, int placeholder) {
        this.tag = tag;
        this.type = type;
        this.cursor = cursor;
        this.placeholder = placeholder;
    }

    public ElementCategories(int tag, int type, int cursor, int placeholder, String ownText) {
        this.tag = tag;
        this.type = type;
        this.cursor = cursor;
        this.placeholder = placeholder;
        this.ownText = ownText;
    }

    public int getTag() {
        return tag;
    }

    public int setTag(String tag) {
        String[] tags = {"div", "button", "input", "a", "span", "textarea", "label", "img", "svg"};
        for (int i = 0; i < tags.length; i++) {
            if (tags[i].equals(tag)) {
                return i;
            }
        }
        return 10;
    }

    public int getType() {
        return type;
    }

    public int setType(String type) {
        String[] types = {"hidden", "button", "checkbox", "file", "image", "password", "radio", "reset", "submit", "text",
                "color", "date", "datetime", "datetime-local", "email", "number", "range", "search", "tel", "time", "url",
                "month", "week"};
        for (int i = 0; i < types.length; i++) {
            if (types[i].equals(type)) {
                return i;
            }
        }
        return 24;
    }

    public int getCursor() {
        return cursor;
    }

    public int setCursor(String cursor) {
        String[] cursors = {"default", "pointer", "text", "crosshair", "help", "move", "progress", "wait", "n-resize",
                "ne-resize", "e-resize", "se-resize", "s-resize", "sw-resize", "w-resize", "nw-resize"};
        for (int i = 0; i < cursors.length; i++) {
            if (cursors[i].equals(cursor)) {
                return i;
            }
        }
        return 17;
    }

    public int getBorder() {
        return border;
    }

    public int setBorder(boolean border) {
        if (!border) {
            return 0;
        } else {
            return 1;
        }
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public int setPlaceholder(boolean placeholder) {
        if (!placeholder) {
            return 0;
        } else {
            return 1;
        }
    }

    public String getOwnText() {
        return ownText;
    }

    public void setOwnText(String ownText) {
        this.ownText = ownText;
    }
}
