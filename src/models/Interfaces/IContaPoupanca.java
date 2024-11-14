package models.Interfaces;

public interface IContaPoupanca {
    void DepositarCP(Double valor, Long senha);

    void SacarCP(Double valor, Long senha);
}
