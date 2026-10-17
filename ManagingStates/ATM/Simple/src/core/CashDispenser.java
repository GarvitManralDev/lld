package core;

import enums.Denomination;
import exceptions.ATMException;

import java.util.EnumMap;
import java.util.Map;

public class CashDispenser {
    private final Map<Denomination, Integer> billCount;

    public CashDispenser(){
        this.billCount = new EnumMap<>(Denomination.class);
        for(Denomination denomination : Denomination.values()){
            billCount.put(denomination, 100);
        }
    }

    public synchronized int getTotalCash(){
        int total = 0;
        for(Denomination denomination : Denomination.values()){
            total += denomination.getValue() * billCount.get(denomination);
        }
        return total;
    }

    public synchronized Map<Denomination, Integer> dispense(int amount) {
        Map<Denomination, Integer> bills = new EnumMap<>(Denomination.class);

        for (Denomination denomination : Denomination.values()) {
            int deno = denomination.getValue();
            int count = amount / deno;

            if (count > billCount.get(denomination)) {
                count = billCount.get(denomination);
            }

            bills.put(denomination, count);
            amount -= count * deno;
        }

        if (amount > 0) {
            throw new ATMException("Insufficient cash or invalid amount");
        }

        for (Denomination denomination : Denomination.values()) {
            billCount.put(
                    denomination,
                    billCount.get(denomination) - bills.get(denomination)
            );
        }

        return bills;
    }
}
