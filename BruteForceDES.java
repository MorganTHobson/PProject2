import javax.crypto.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import javax.crypto.spec.*;
import java.util.Random;

import java.io.PrintStream;


class BruteForceDES implements Runnable
{

  public BruteForceDES()
  {
  }

  public void run()
  {
  }

  public static void main (String[] args) throws IOException
  {
        if ( 3 != args.length )
        {
            System.out.println ("Usage: java BruteForceDES #threads key_size_in_bits filename");
            return;
        }

        // create object to printf to the console
        PrintStream p = new PrintStream(System.out);

        // Get the argument
        long keybits = Long.parseLong ( args[0] );

        long maxkey = ~(0L);
        maxkey = maxkey >>> (64 - keybits);

        // Create a simple cipher
        SealedDES enccipher = new SealedDES ();

        // Get a number between 0 and 2^64 - 1
        Random generator = new Random ();
        long key =  generator.nextLong();

        // Mask off the high bits so we get a short key
        key = key & maxkey;

        // Set up a key
        enccipher.setKey ( key );

        // Get the filename
        String filename = args[1];
        // Read in the file to encrypt
        File inputFile = new File(filename);

        // Turn it into a string
        if (!inputFile.exists()) {
            System.err.println("error: Input file not found.");
            System.exit(1);
        }
        byte[] encoded = Files.readAllBytes(Paths.get(filename));

        String plainstr = new String(encoded, StandardCharsets.US_ASCII);

        // Encrypt
        SealedObject sldObj = enccipher.encrypt ( plainstr );
  }
}
