package bolicho.model;

public enum Status {

    EM_ANDAMENTO("Em andamento"),
    ATRASADO("Atrasado"),
    FINALIZADO("Finalizado"),
    CANCELADO("Cancelado");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

