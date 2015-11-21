package ua.yandex.shad.tries;
public class DinamicString {
    private String s;
    public DinamicString() {
        s = null;
    }
    public DinamicString(String s) {
        this.s = s;
    }
    public DinamicString(String ...s) {
        char[] sChar = new char[s.length];
        for (int i = 0; i < s.length; i++) {
            s[i].toCharArray();
        }
    }
    public DinamicString addChar(char c) {
        int len = this.s.length();
        char[] SinChar = new char[len + 1];
        SinChar = s.toCharArray();
        SinChar[len] = c;
        DinamicString res = new DinamicString();
        res.s = SinChar.toString();
        return res;
    }
}