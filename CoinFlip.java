import java.util.Random;


class Coin
{
    private Random rand;

    public Coin()
    {
        this.rand = new Random();
    }

    public boolean flip()
    {
        return this.rand.nextBoolean();
    }
}

public static void main (String[] args)
{
    if (2 != args.length)
    {
        System.out.println("Usage: java CoinFlip #threads #flips");
        return;
    }

    
}
