/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package des;

/**
 *
 * @author Aya Mazloum
 */

public class DESEncDec {
    public static class DES {
        // Constants
        // First Key-Permutation Table
        private int[] PC1
            = { 57, 49, 41, 33, 25, 17, 9,  1,  58, 50,
                42, 34, 26, 18, 10, 2,  59, 51, 43, 35,
                27, 19, 11, 3,  60, 52, 44, 36, 63, 55,
                47, 39, 31, 23, 15, 7,  62, 54, 46, 38,
                30, 22, 14, 6,  61, 53, 45, 37, 29, 21,
                13, 5,  28, 20, 12, 4 };
        
        // Second Key-Permutation Table
        private int[] PC2
            = { 14, 17, 11, 24, 1,  5,  3,  28, 15, 6,
                21, 10, 23, 19, 12, 4,  26, 8,  16, 7,
                27, 20, 13, 2,  41, 52, 31, 37, 47, 55,
                30, 40, 51, 45, 33, 48, 44, 49, 39, 56,
                34, 53, 46, 42, 50, 36, 29, 32 };
        
        // Number of shifts for each round
        private int[] shiftBits = { 1, 1, 2, 2, 2, 2, 2, 2,
                            1, 2, 2, 2, 2, 2, 2, 1 };
        
        // Initial Permutation Table
        private int[] IP
            = { 58, 50, 42, 34, 26, 18, 10, 2,  60, 52, 44,
                36, 28, 20, 12, 4,  62, 54, 46, 38, 30, 22,
                14, 6,  64, 56, 48, 40, 32, 24, 16, 8,  57,
                49, 41, 33, 25, 17, 9,  1,  59, 51, 43, 35,
                27, 19, 11, 3,  61, 53, 45, 37, 29, 21, 13,
                5,  63, 55, 47, 39, 31, 23, 15, 7 };
        
        // Inverse Initial Permutation Table
        int[] IP1
            = { 40, 8,  48, 16, 56, 24, 64, 32, 39, 7,  47,
                15, 55, 23, 63, 31, 38, 6,  46, 14, 54, 22,
                62, 30, 37, 5,  45, 13, 53, 21, 61, 29, 36,
                4,  44, 12, 52, 20, 60, 28, 35, 3,  43, 11,
                51, 19, 59, 27, 34, 2,  42, 10, 50, 18, 58,
                26, 33, 1,  41, 9,  49, 17, 57, 25 };
        
        // Expansion Bit-Selection Table
        private int[] EP = { 32, 1,  2,  3,  4,  5,  4,  5,  6,  7,
                     8,  9,  8,  9,  10, 11, 12, 13, 12, 13,
                     14, 15, 16, 17, 16, 17, 18, 19, 20, 21,
                     20, 21, 22, 23, 24, 25, 24, 25, 26, 27,
                     28, 29, 28, 29, 30, 31, 32, 1 };
        
        // S-box Table
        private int[][][] sbox
            = { { { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6,
                    12, 5, 9, 0, 7 },
                  { 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12,
                    11, 9, 5, 3, 8 },
                  { 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7,
                    3, 10, 5, 0 },
                  { 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14,
                    10, 0, 6, 13 } },
 
                { { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13,
                    12, 0, 5, 10 },
                  { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10,
                    6, 9, 11, 5 },
                  { 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6,
                    9, 3, 2, 15 },
                  { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12,
                    0, 5, 14, 9 } },
                { { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7,
                    11, 4, 2, 8 },
                  { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14,
                    12, 11, 15, 1 },
                  { 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12,
                    5, 10, 14, 7 },
                  { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3,
                    11, 5, 2, 12 } },
                { { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5,
                    11, 12, 4, 15 },
                  { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12,
                    1, 10, 14, 9 },
                  { 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3,
                    14, 5, 2, 8, 4 },
                  { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11,
                    12, 7, 2, 14 } },
                { { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15,
                    13, 0, 14, 9 },
                  { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15,
                    10, 3, 9, 8, 6 },
                  { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5,
                    6, 3, 0, 14 },
                  { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9,
                    10, 4, 5, 3 } },
                { { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4,
                    14, 7, 5, 11 },
                  { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14,
                    0, 11, 3, 8 },
                  { 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10,
                    1, 13, 11, 6 },
                  { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7,
                    6, 0, 8, 13 } },
                { { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7,
                    5, 10, 6, 1 },
                  { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12,
                    2, 15, 8, 6 },
                  { 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6,
                    8, 0, 5, 9, 2 },
                  { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15,
                    14, 2, 3, 12 } },
                { { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14,
                    5, 0, 12, 7 },
                  { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11,
                    0, 14, 9, 2 },
                  { 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13,
                    15, 3, 5, 8 },
                  { 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0,
                    3, 5, 6, 11 } } };// Straight Permutation Table
        // Straight Permutation
        private int[] P
            = { 16, 7, 20, 21, 29, 12, 28, 17, 1,  15, 23,
                26, 5, 18, 31, 10, 2,  8,  24, 14, 32, 27,
                3,  9, 19, 13, 30, 6,  22, 11, 4,  25 };
        
        private String encrypt(String plainText, String key) {
            // Step 1: Create 16 subkeys, each of which is 48-bits long
            // Key Transformation
            key = hexToBin(key);
            String[] K = createSubkeys(key);
            
            // Step 2: Encode each 64-bit block of plain text
            // Initial Permutation
            plainText = permutate(plainText, IP);
            
            // 16 rounds
            for (int i = 0; i < 16; i++) {
                plainText = round(plainText, K[i]);
                //System.out.println(i + ": " + binToHex(plainText));
            }
            
            // 32-bit swap
            plainText = plainText.substring(32, 64) 
                    + plainText.substring(0, 32);
            
            // Final permutation
            plainText = permutate(plainText, IP1);

            return binToHex(plainText);
        }
        
        private String decrypt(String plainText, String key) {
            // Step 1: Create 16 subkeys, each of which is 48-bits long
            // Key Transformation
            key = hexToBin(key);
            String[] K = createSubkeys(key);
            
            // Step 2: Encode each 64-bit block of plain text
            // Initial Permutation
            plainText = permutate(plainText, IP);
            
            // 16 rounds
            for (int i = 15; i >= 0; i--) {
                plainText = round(plainText, K[i]);
                //System.out.println(i + ": " + binToHex(plainText));
            }
            
            // 32-bit swap
            plainText = plainText.substring(32, 64) 
                    + plainText.substring(0, 32);
            
            // Final permutation
            plainText = permutate(plainText, IP1);

            return binToHex(plainText);
        }
        
        private String[] createSubkeys(String key) {
            // First key permutation
            key = permutate(key, PC1);
            String[] subkeys = new String[16];
            for (int i = 0; i < 16; i++) {
                key = leftCircularShift(key.substring(0, 28), shiftBits[i]) 
                        + leftCircularShift(key.substring(28, 56), shiftBits[i]);
                // Second key permutation
                subkeys[i] = permutate(key, PC2);
            }
            return subkeys;
        }
        
        private String leftCircularShift(String input, int numBits) {
            StringBuilder output = new StringBuilder("");
            for (int i = 0; i < input.length() - numBits; i++) {
                output.append(input.charAt(i + numBits));
            }
            output.append(input.substring(0, numBits));
            return output.toString();
        }
        
        private String round(String input, String key) {
            // Divide the data into L and R halfs
            String L = input.substring(0, 32);
            String R = input.substring(32, 64);
            String temp = R;
            
            // Expansion Permutation
            temp = permutate(temp, EP);
            // xor temp and round key
            temp = xor(temp, key);
            // s-box
            temp = sBox(temp);
            // Straight Permutation
            temp = permutate(temp, P);
            // xor sbox and L
            L = xor(L, temp);
            
            // swap and return
            return R + L;
        }
        
        private String sBox(String input) {
            StringBuilder output = new StringBuilder("");
            String bin, s;
            int row, col;
            for (int i = 0; i < 8; i++) {
                bin = input.substring(i*6, i*6 + 6);
                row = Integer.parseInt(bin.charAt(0) + "" + bin.charAt(5), 2);
                col = Integer.parseInt(bin.substring(1, 5), 2);
                s = Integer.toBinaryString(sbox[i][row][col]);
                // prepend 0's to maintain length
                while (s.length() < 4)
                    s = "0" + s;
                output.append(s);
            }
            return output.toString();
        }
        
        private String xor(String a, String b) {
            long t_a = Long.parseUnsignedLong(a, 2);
            long t_b = Long.parseUnsignedLong(b, 2);
            t_a = t_a ^ t_b;
            a = Long.toBinaryString(t_a);
            // prepend 0's to maintain length
            while (a.length() < b.length())
                a = "0" + a;
            return a;
        }
        
        // Permutate input according to the specified sequence
        private String permutate(String input, int[] sequence) {
            String output = "";
            for ( int i = 0; i < sequence.length; i++)
                output = output + input.charAt(sequence[i] - 1);
            return output;
        }

        // Convert hexadecimal to binary
        private String hexToBin(String input)
        {
            int n = input.length() * 4;
            input = Long.toBinaryString(
                Long.parseUnsignedLong(input, 16));
            // prepend 0's to maintain length
            while (input.length() < n)
                input = "0" + input;
            return input;
        }

        // Convert binary to hexadecimal
        private String binToHex(String input)
        {
            int n = (int)input.length() / 4;
            input = Long.toHexString(
                Long.parseUnsignedLong(input, 2));
            while (input.length() < n)
                input = "0" + input;
            return input;
        }
    }
    
    // Driver code
    public static void main(String[] args) {
        String plainText, key, cipherText;
        plainText = "Hello World!";
        key = "AABB09182736CCDD";
        cipherText = "";
        
        DES des = new DES();
        System.out.println("DES Encryption");
        System.out.println("Plain Text: " + plainText);
        plainText = strToBinary(plainText);
        String[] blocks = textToBlocks(plainText);
        for (int i=0; i<blocks.length; i++)
            cipherText += des.encrypt(blocks[i], key);
        System.out.println("Cipher Text: " + cipherText);
        
        System.out.println("\nDES Decryption");
        System.out.println("Cipher Text: " + cipherText);
        plainText = "";
        int cblocksSize = cipherText.length() / 16;
        String[] cblocks = new String[cblocksSize];
        for (int i = 0; i < cblocksSize; i++) {
            cblocks[i] = cipherText.substring(i*16, i*16+16);
            plainText += des.hexToBin(cblocks[i]);
        }
        blocks = textToBlocks(plainText);
        cipherText = "";
        for (int i=0; i<blocks.length; i++)
            cipherText += des.decrypt(blocks[i], key);
        System.out.println("Plain Text: " + hexToAscii(cipherText));
    }
    
    // Divides the
    public static String[] textToBlocks(String text) {
        int numBlocks = text.length()/64;
        int x;
        if(text.length()%64 != 0) {
            numBlocks++;
            x = numBlocks % 64;
            while(x < 64) {
                text += "0";
                x++;
            }
        }
        String[] blocks = new String[numBlocks];
        for (int i = 0; i < numBlocks; i++) {
            blocks[i] = text.substring(0, 64);
            text = text.substring(64, text.length());
        }
        return blocks;
    }
    
    // Convert string to binary
    public static String strToBinary(String s)
    {
        int n = s.length();
        String charBin, bin = "";
        for (int i = 0; i < n; i++)
        {
            // convert each char to
            // ASCII value
            int val = Integer.valueOf(s.charAt(i));

            // Convert ASCII value to binary
            charBin = "";
            while (val > 0)
            {
                if (val % 2 == 1)
                {
                    charBin += '1';
                }
                else
                    charBin += '0';
                val /= 2;
            }
            charBin = reverse(charBin);
            while(charBin.length() < 8)
                charBin = "0" + charBin;
            bin += charBin;
        }
        return bin;
    }

    // Reverse string
    public static String reverse(String input)
    {
        char[] a = input.toCharArray();
        int l, r;
        r = a.length - 1;

        for (l = 0; l < r; l++, r--)
        {
            // Swap values of l and r
            char temp = a[l];
            a[l] = a[r];
            a[r] = temp;
        }
        return String.valueOf(a);
    }
    
    // Convert from hexadecimal to text
    public static String hexToAscii(String input)
    {
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < input.length(); i += 2) {
            String str = input.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }
}
