package user;

/**
 * @author Smoke
 */
public class User {

    private int nNexonCash = 0, nVotePoints = 0, nDonationPoints = 0, nMesos = 0;

    public User() {
    }

    public int GetNexonCash() {
        return nNexonCash;
    }

    public void SetNexonCash(int nNexonCash) {
        this.nNexonCash = nNexonCash;
    }


    public int GetVotePoints() {
        return nVotePoints;
    }

    public void SetVotePoints(int nVotePoints) {
        this.nVotePoints = nVotePoints;
    }

    public int GetDonationPoints() {
        return nDonationPoints;
    }

    public void SetDonationPoints(int nDonationPoints) {
        this.nDonationPoints = nDonationPoints;
    }

    public int GetMesos() {
        return nMesos;
    }

    public void SetMesos(int nMesos) {
        this.nMesos = nMesos;
    }

    public boolean HasItem(int nItemID) {
        return false;
    }

    public int GetItemCount(int nItemID) {
        return 0;
    }

    public void ItemExchange(int nItemID, int nCount) {

    }

    public void InventoryExchange(int nMoney, int... nItemIDThenCount_RepeatedPattern) {

    }
}
