import java.util.Random;

class CoinFlip implements Runnable
{
  private Random rand;

  private static int num_flips;
  private static int heads = 0;

  public CoinFlip()
  {
    rand = new Random();
  }

  public void run()
  {
    int flip_counter;
    while(true)
    {
      synchronized(CoinFlip.class)
      {
        flip_counter = num_flips--;
      }

      if (flip_counter <= 0) {return;}

      if (rand.nextBoolean())
      {
        synchronized(CoinFlip.class){++heads;}
      }
        
    }
  }

  public static void main (String[] args)
  {
    if (2 != args.length)
    {
      System.out.println("Usage: java CoinFlip #threads #flips");
      return;
    }

    int num_threads = Integer.parseInt(args[0]);

    num_flips = Integer.parseInt(args[1]);

    Thread[] threads = new Thread[num_threads];

    long time = System.currentTimeMillis();

    for ( int i=0; i<num_threads; i++ )
    {
      threads[i] = new Thread ( new CoinFlip() );
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
    System.out.printf("%d heads in %s coin tosses\n", heads, args[1]);
    System.out.printf("Elapsed time: %dms\n", System.currentTimeMillis() - time);
  }
}
