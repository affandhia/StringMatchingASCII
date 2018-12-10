/**
 * Copyright © 2000–2017, Robert Sedgewick and Kevin Wayne.
 *         Last updated: Fri Oct 20 12:50:46 EDT 2017.
 * Modified by Affan Dhia Ardhiva
 *         Last modified: Tue Dec 11 03:53:20 WIB 2018
 * source: https://algs4.cs.princeton.edu/53substring/Brute.java.html
 */

public class NaiveStringMatcher {

    /***************************************************************************
     *  String versions.
     ***************************************************************************/

    // return offset of first match or n if no match
    public static int search1(String pat, String txt) {
        int m = pat.length();
        int n = txt.length();

        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (txt.charAt(i+j) != pat.charAt(j))
                    break;
            }
            if (j == m) return i;            // found at offset i
        }

        return -1;                            // not found
    }

    // return offset of first match or N if no match
    public static int search2(String pat, String txt) {
        int m = pat.length();
        int n = txt.length();
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            if (txt.charAt(i) == pat.charAt(j)) j++;
            else {
                i -= j;
                j = 0;
            }
        }
        if (j == m) return i - m;    // found
        else        return -1;        // not found
    }


    /***************************************************************************
     *  char[] array versions.
     ***************************************************************************/

    // return offset of first match or n if no match
    public static int search1(char[] pattern, char[] text) {
        int m = pattern.length;
        int n = text.length;

        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (text[i+j] != pattern[j])
                    break;
            }
            if (j == m) return i;            // found at offset i
        }
        return -1;                            // not found
    }

    // return offset of first match or n if no match
    public static int search2(char[] pattern, char[] text) {
        int m = pattern.length;
        int n = text.length;
        int i, j;
        for (i = 0, j = 0; i < n && j < m; i++) {
            if (text[i] == pattern[j]) j++;
            else {
                i -= j;
                j = 0;
            }
        }
        if (j == m) return i - m;    // found
        else        return -1;        // not found
    }


}


