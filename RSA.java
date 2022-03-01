import java.math.BigInteger;
import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;

 
public class RSA {
    public static void main(String[]args){
        //Size of primes
        int BIT_LENGTH = 4096;
        Random rand = new Random();
        //Generate primes and other necessary values
        BigInteger p = BigInteger.probablePrime(BIT_LENGTH / 2, rand);
        BigInteger q = BigInteger.probablePrime(BIT_LENGTH / 2, rand);
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1)));
        BigInteger e;
        BigInteger d;
        do {
            e = new BigInteger(phi.bitLength(), rand);
        } while (e.compareTo(BigInteger.valueOf(1)) <= 0 || e.compareTo(phi) >= 0 || !e.gcd(phi).equals(BigInteger.valueOf(1)));
        d = e.modInverse(phi);

        Scanner input = new Scanner(System.in);
        String message;
        System.out.println("Text: ");
        message = input.nextLine();
        String[] messageArray = message.split(" "); // split word
        int count = 0;
        BigInteger[] byteArray = new BigInteger[10000]; 
        BigInteger byteM;
        
        // string -> number
        for (String a : messageArray) {
            if (a.length() > 0) {
                byteM = new BigInteger(a.getBytes());
                byteArray[count] = byteM;
                count++;
            } 
        } 

        // encode 
        BigInteger[] cipherArray = new BigInteger[10000];
        for (int index = 0; index < count; index++) {
            cipherArray[index] = byteArray[index].modPow(e,n);
        }
        System.out.println("Ciphertext:");
        for (int index = 0; index < count; index++) {
            System.out.print(cipherArray[index] + " ");
        }
        System.out.println();

        // decode
        BigInteger[] decryptedArray = new BigInteger[10000];
        for (int index = 0; index < count; index++) {
            decryptedArray[index] = cipherArray[index].modPow(d,n);
        }

        // number -> string 
        String decryptedText = "";
        for (int index = 0; index < count; index++) {
            decryptedText = decryptedText + new String(decryptedArray[index].toByteArray()) + " ";
        }
        System.out.println("Decrypted message: " + decryptedText);
    }
}