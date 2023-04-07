
package client.legalease.Model.SummeryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SummeryData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("clientId")
    @Expose
    private String clientId;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("bank_balance_as_per_bank")
    @Expose
    private String bankBalanceAsPerBank;
    @SerializedName("bank_balance_as_per_book")
    @Expose
    private String bankBalanceAsPerBook;
    @SerializedName("total_income")
    @Expose
    private String totalIncome;
    @SerializedName("total_expenses")
    @Expose
    private String totalExpenses;
    @SerializedName("net_profit_loss")
    @Expose
    private String netProfitLoss;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBankBalanceAsPerBank() {
        return bankBalanceAsPerBank;
    }

    public void setBankBalanceAsPerBank(String bankBalanceAsPerBank) {
        this.bankBalanceAsPerBank = bankBalanceAsPerBank;
    }

    public String getBankBalanceAsPerBook() {
        return bankBalanceAsPerBook;
    }

    public void setBankBalanceAsPerBook(String bankBalanceAsPerBook) {
        this.bankBalanceAsPerBook = bankBalanceAsPerBook;
    }

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(String totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public String getNetProfitLoss() {
        return netProfitLoss;
    }

    public void setNetProfitLoss(String netProfitLoss) {
        this.netProfitLoss = netProfitLoss;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
