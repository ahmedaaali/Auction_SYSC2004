import java.util.*;

/**
 * A simple model of an auction.
 * The auction maintains a list of lots of arbitrary length.
 *
 * @author David J. Barnes and Michael Kolling.
 * @version 2006.03.30
 *
 * @author (of AuctionSkeleton) Lynn Marshall
 * @version 2.0
 * 
 * @author Ahmed Ali (101181126).
 * @version February 10th, 2022.
 * 
 */
public class Auction
{
    private ArrayList<Lot> lots;

    private int nextLotNumber;

    private boolean openAuction;

    /**
     * Create a new auction.
     */
    public Auction()
    {
        lots = new ArrayList<Lot>();
        nextLotNumber = 1;
        openAuction = true;
    }
    
    /**
     * Create an auction from an old auction's unsold lots, or if empty then
     * create a new auction.
     */
    public Auction(Auction auction)
    {
        if (auction.openAuction) {
            lots = new ArrayList<Lot>();
            nextLotNumber = 1;
        } else {
            lots = auction.getNoBids();
            nextLotNumber = auction.nextLotNumber;
        }
        openAuction = true;
    }
    
    /**
     * Enter a new lot into the auction.  Returns false if the
     * auction is not open or if the description is null.
     *
     * @param description A description of the lot.
     * 
     * @return true if successful or false if the auction is not open or if the
     * description is null.
     */
    public boolean enterLot(String description)
    {
        if (openAuction && description != null) {
            lots.add(new Lot(nextLotNumber, description));
            nextLotNumber++;
            return true;
        }
        return false;
    }

    /**
     * Show the full list of lots in this auction.
     */
    public void showLots()
    {
        System.out.println();
        if (lots.size() == 0) {
            System.out.println("There are no lots in the auction yet!");
        } else {
            for(Lot lot : lots) {
                System.out.println(lot.toString());
            }
        }
    }
    
    /**
     * Method for bidding for a lot.
     * Prints a message indicating whether the bid is successful or not.
     * 
     * Returns false if the auction is closed, the lot doesn't
     * exist, the bidder is null, or the bid was not positive
     * and true otherwise.
     *
     * @param number The lot number being bid for.
     * @param bidder The person bidding for the lot.
     * @param value  The value of the bid.
     * 
     * @return true if successful or false if the auction is closed, the lot 
     * doesn't exist, the bidder is null, or the bid was not positive.
     */
    public boolean bidFor(int lotNumber, Person bidder, long value)
    {
        System.out.println();
        Lot selectedLot = getLot(lotNumber);
        if (selectedLot != null && openAuction && bidder != null && value > 0) {
            Bid bid = new Bid(bidder, value);
            boolean successful = selectedLot.bidFor(bid);
            if (successful) {
                System.out.println("The bid for lot number " + lotNumber +
                                    " was successful.");
                System.out.println("The high bidder is now " + bidder.getName() + 
                                   " with a bid of $" + value + ".");
                return true;
            
            }
        }
        System.out.println("The bid for lot number " + lotNumber +
                            " was NOT successful.");
        if (selectedLot != null) {
            System.out.println("Lot number: " + lotNumber + " already has a bid of: "
                                + selectedLot.getHighestBid().getValue());
        }
            
        return false;
    }


    /**
     * Return the lot with the given number.
     * Returns null if the lot does not exist.
     *
     * @param lotNumber The number of the lot to return.
     *
     * @return the Lot with the given number, or null if the lot does not exist.
     */
    public Lot getLot(int lotNumber)
    {
        if((lotNumber >= 1) && (lotNumber < nextLotNumber)) {
            for(Lot lot : lots) {
                if (lot.getNumber() == lotNumber) {
                    return lot;
                }
            }
        }
        System.out.println("Lot number: " + lotNumber + " does not exist.");
        return null;
    }
    
    /**
     * Closes the auction and prints information on the lots.
     * 
     * @return true if successful or false if the auction is already closed.
     */
    public boolean close()
    {
        showLots();
        if (openAuction) {
            openAuction = false;
            return true;
        }
        return false;
    }
    
    /**
     * Returns an ArrayList containing all the items that have no bids so far.
     * (or have not sold if the auction has ended).
     * 
     * @return an ArrayList of the Lots which currently have no bids.
     */
    public ArrayList<Lot> getNoBids()
    {
       ArrayList<Lot> noBidslot = new ArrayList<Lot>();
       for(Lot lot : lots) {
            if (lot.getHighestBid() == null) {
               noBidslot.add(new Lot(lot.getNumber(), lot.getDescription()));
           }
       }
       return noBidslot;
    }
    
    /**
     * Removes the lot with the given lot number, as long as the lot has
     * no bids, and the auction is open.
     *
     * @param number The number of the lot to be removed.
     * 
     * @return true if successful, false otherwise.
     */
    public boolean removeLot(int number)
    {
        if (openAuction && number >= 1 && number < nextLotNumber){
            Iterator<Lot> itLots = lots.iterator();
            while (itLots.hasNext()) {
                Lot selectedLot = itLots.next();
                if(selectedLot.getNumber() == number && selectedLot.getHighestBid() == null) {
                    itLots.remove();
                    return true;
                }
            }
        }
        return false;
    }
}