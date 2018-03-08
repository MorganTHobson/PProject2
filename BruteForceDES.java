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

  private static SealedObject sldObj;
  private static String plainstr;

  private long time_start;
  private SealedDES decipher;
  private long key_start;
  private long key_end;

  public BruteForceDES(long time, long start, long end)
  {
    decipher = new SealedDES();
    time_start = time;
    key_start = start;
    key_end = end;
  }

  public void run()
  {
    // Search for the right key
    for ( long i = key_start; i < key_end; i++ )
    {
      // Set the key and decipher the object
      decipher.setKey ( i );
      String decryptstr = decipher.decrypt ( sldObj );

      // Does the object contain the known plaintext
      if (( decryptstr != null ) && ( decryptstr.contains(plainstr)))
      {
        //  Remote printlns if running for time.
        System.out.println (  "Found decrypt key " + i + " producing message: " + decryptstr );
      }

      // Update progress every once in awhile.
      //  Remote printlns if running for time.
      if ( i % 100000 == 0 )
      {
        long elapsed = System.currentTimeMillis() - time_start;
        System.out.println ( "Searched key number " + i + " at " + elapsed + " milliseconds.");
      }
    }
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
    long keybits = Long.parseLong ( args[1] );
    int num_threads = Integer.parseInt ( args[0] );

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
    String filename = args[2];
    // Read in the file to encrypt
    File inputFile = new File(filename);

    // Turn it into a string
    if (!inputFile.exists()) {
        System.err.println("error: Input file not found.");
        System.exit(1);
    }
    byte[] encoded = Files.readAllBytes(Paths.get(filename));

    plainstr = new String(encoded, StandardCharsets.US_ASCII);

    // Encrypt
    sldObj = enccipher.encrypt ( plainstr );
    
    // Get and store the current time -- for timing
    long runstart;
    runstart = System.currentTimeMillis();

    Thread[] threads = new Thread[num_threads];

    for ( int i=0; i<num_threads; i++ )
    {
      threads[i] = new Thread ( new BruteForceDES(runstart, i*maxkey, (i+1)*maxkey) );
      threads[i].start();
    }


    for ( int i=0; i<num_threads; i++ )
    {
      try
      {
        threads[i].join();
      }
      catch (InterruptedException e)
      {
         System.out.println("Thread interrupted.  Exception: " + e.toString() +
                           " Message: " + e.getMessage()) ;
        return;
      }
    }

    // Output search time
    long elapsed = System.currentTimeMillis() - runstart;
    long keys = maxkey + 1;
    System.out.println ( "Completed search of " + keys + " keys at " + elapsed + " milliseconds.");
  }
}
