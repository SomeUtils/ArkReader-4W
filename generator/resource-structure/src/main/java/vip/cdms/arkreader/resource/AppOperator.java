package vip.cdms.arkreader.resource;

public interface AppOperator {
    Operator[] getSortedOperators();

    byte[] getProfessionIcon(OperatorProfession profession);
}
