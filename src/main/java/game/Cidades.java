package game;

public enum Cidades {
    VANCOUVER("Vancouver"),
    ATLANTA("Atlanta"),
    SEATTLE("Seattle"),
    CALGARY("Calgary"),
    PORTLAND("Portland"),
    BOSTON("Boston"),
    CHARLESTON("Charleston"),
    MIAMI("Miami"),
    NEW_ORLEANS("New Orleans"),
    NEW_YORK("New York"),
    CHICAGO("Chicago"),
    PITTSBURGH("Pittsburgh"),
    HELENA("Helena"),
    WINNIPEG("Winnipeg"),
    DALLAS("Dallas"),
    SAINT_LOUIS("Saint Louis"), // ou St. Louis
    TORONTO("Toronto"),
    HOUSTON("Houston"),
    DENVER("Denver"),
    KANSAS_CITY("Kansas City"),
    OKLAHOMA_CITY("Oklahoma City"),
    OMAHA("Omaha"),
    PHOENIX("Phoenix"),
    DULUTH("Duluth"),
    EL_PASO("El Paso"),
    SANTA_FE("Santa Fe"),
    SAULT_ST_MARIE("Sault St. Marie"),
    LITTLE_ROCK("Little Rock"),
    SALT_LAKE_CITY("Salt Lake City"),
    LOS_ANGELES("Los Angeles"),
    MONTREAL("Montreal"),
    NASHVILLE("Nashville"),
    WASHINGTON("Washington"),
    LAS_VEGAS("Las Vegas"),
    RALEIGH("Raleigh"),
    SAN_FRANCISCO("San Francisco");

    private final String nomeFormatado;

    // Construtor do Enum
    Cidades(String nomeFormatado) {
        this.nomeFormatado = nomeFormatado;
    }

    // Método para pegar o nome bonito (ex: "New York" ao invés de NEW_YORK)
    @Override
    public String toString() {
        return nomeFormatado;
    }
}