package io.jadon.election

/**
 * Different Ideologies have stances on kinds of political issues.
 * These numbers are taken from: https://github.com/8values/8values.github.io/blob/master/ideologies.js
 */
enum class Ideology(val displayName: String, val values: Map<Category, Double>) {
    ANARCHO_COMMUNISM(
        "Anarcho-Communism",
        mapOf(
            Category.ECONOMIC to 100.0,
            Category.DIPLOMATIC to 50.0,
            Category.CIVIL to 90.0,
            Category.SOCIETAL to 90.0
        )
    ),
    LIBERTARIAN_COMMUNISM(
        "Libertarian Communism",
        mapOf(
            Category.ECONOMIC to 100.0,
            Category.DIPLOMATIC to 70.0,
            Category.CIVIL to 80.0,
            Category.SOCIETAL to 80.0
        )
    ),
    TROTSKYISM(
        "Trotskyism",
        mapOf(
            Category.ECONOMIC to 100.0,
            Category.DIPLOMATIC to 100.0,
            Category.CIVIL to 60.0,
            Category.SOCIETAL to 80.0
        )
    ),
    MARXISM(
        "Marxism",
        mapOf(
            Category.ECONOMIC to 100.0,
            Category.DIPLOMATIC to 70.0,
            Category.CIVIL to 40.0,
            Category.SOCIETAL to 80.0
        )
    ),
    DE_LEONISM(
        "De Leonism",
        mapOf(
            Category.ECONOMIC to 100.0,
            Category.DIPLOMATIC to 30.0,
            Category.CIVIL to 30.0,
            Category.SOCIETAL to 80.0
        )
    ),
    LENINISM(
        "Leninism",
        mapOf(
            Category.ECONOMIC to 100.0,
            Category.DIPLOMATIC to 40.0,
            Category.CIVIL to 20.0,
            Category.SOCIETAL to 70.0
        )
    ),
    STALINISM(
        "Stalinism",
        mapOf(
            Category.ECONOMIC to 100.0,
            Category.DIPLOMATIC to 20.0,
            Category.CIVIL to 0.0,
            Category.SOCIETAL to 60.0
        )
    ),
    RELIGIOUS_COMMUNISM(
        "Religious Communism",
        mapOf(
            Category.ECONOMIC to 100.0,
            Category.DIPLOMATIC to 50.0,
            Category.CIVIL to 30.0,
            Category.SOCIETAL to 30.0
        )
    ),
    STATE_SOCIALISM(
        "State Socialism",
        mapOf(
            Category.ECONOMIC to 80.0,
            Category.DIPLOMATIC to 30.0,
            Category.CIVIL to 30.0,
            Category.SOCIETAL to 70.0
        )
    ),
    THEOCRATIC_SOCIALISM(
        "Theocratic Socialism",
        mapOf(
            Category.ECONOMIC to 80.0,
            Category.DIPLOMATIC to 50.0,
            Category.CIVIL to 30.0,
            Category.SOCIETAL to 20.0
        )
    ),
    RELIGIOUS_SOCIALISM(
        "Religious Socialism",
        mapOf(
            Category.ECONOMIC to 80.0,
            Category.DIPLOMATIC to 50.0,
            Category.CIVIL to 70.0,
            Category.SOCIETAL to 20.0
        )
    ),
    DEMOCRATIC_SOCIALISM(
        "Democratic Socialism",
        mapOf(
            Category.ECONOMIC to 80.0,
            Category.DIPLOMATIC to 50.0,
            Category.CIVIL to 50.0,
            Category.SOCIETAL to 80.0
        )
    ),
    REVOLUTIONARY_SOCIALISM(
        "Revolutionary Socialism",
        mapOf(
            Category.ECONOMIC to 80.0,
            Category.DIPLOMATIC to 20.0,
            Category.CIVIL to 50.0,
            Category.SOCIETAL to 70.0
        )
    ),
    LIBERTARIAN_SOCIALISM(
        "Libertarian Socialism",
        mapOf(
            Category.ECONOMIC to 80.0,
            Category.DIPLOMATIC to 80.0,
            Category.CIVIL to 80.0,
            Category.SOCIETAL to 80.0
        )
    ),
    ANARCHO_SYNDICALISM(
        "Anarcho-Syndicalism",
        mapOf(
            Category.ECONOMIC to 80.0,
            Category.DIPLOMATIC to 50.0,
            Category.CIVIL to 100.0,
            Category.SOCIETAL to 80.0
        )
    ),
    LEFT_WING_POPULISM(
        "Left-Wing Populism",
        mapOf(
            Category.ECONOMIC to 60.0,
            Category.DIPLOMATIC to 40.0,
            Category.CIVIL to 30.0,
            Category.SOCIETAL to 70.0
        )
    ),
    THEOCRATIC_DISTRIBUTISM(
        "Theocratic Distributism",
        mapOf(
            Category.ECONOMIC to 60.0,
            Category.DIPLOMATIC to 40.0,
            Category.CIVIL to 30.0,
            Category.SOCIETAL to 20.0
        )
    ),
    DISTRIBUTISM(
        "Distributism",
        mapOf(
            Category.ECONOMIC to 60.0,
            Category.DIPLOMATIC to 40.0,
            Category.CIVIL to 30.0,
            Category.SOCIETAL to 20.0
        )
    ),
    SOCIAL_LIBERALISM(
        "Social Liberalism",
        mapOf(
            Category.ECONOMIC to 60.0,
            Category.DIPLOMATIC to 60.0,
            Category.CIVIL to 60.0,
            Category.SOCIETAL to 80.0
        )
    ),
    CHRISTIAN_DEMOCRACY(
        "Christian Democracy",
        mapOf(
            Category.ECONOMIC to 60.0,
            Category.DIPLOMATIC to 60.0,
            Category.CIVIL to 50.0,
            Category.SOCIETAL to 30.0
        )
    ),
    SOCIAL_DEMOCRACY(
        "Social Democracy",
        mapOf(
            Category.ECONOMIC to 60.0,
            Category.DIPLOMATIC to 70.0,
            Category.CIVIL to 60.0,
            Category.SOCIETAL to 80.0
        )
    ),
    PROGRESSIVISM(
        "Progressivism",
        mapOf(
            Category.ECONOMIC to 60.0,
            Category.DIPLOMATIC to 80.0,
            Category.CIVIL to 60.0,
            Category.SOCIETAL to 1030.0
        )
    ),
    ANARCHO_MUTUALISM(
        "Anarcho-Mutualism",
        mapOf(
            Category.ECONOMIC to 60.0,
            Category.DIPLOMATIC to 50.0,
            Category.CIVIL to 100.0,
            Category.SOCIETAL to 70.0
        )
    ),
    NATIONAL_TOTALITARIANISM(
        "National Totalitarianism",
        mapOf(
            Category.ECONOMIC to 50.0,
            Category.DIPLOMATIC to 20.0,
            Category.CIVIL to 00.0,
            Category.SOCIETAL to 50.0
        )
    ),
    GLOBAL_TOTALITARIANISM(
        "Global Totalitarianism",
        mapOf(
            Category.ECONOMIC to 50.0,
            Category.DIPLOMATIC to 80.0,
            Category.CIVIL to 00.0,
            Category.SOCIETAL to 50.0
        )
    ),
    TECHNOCRACY(
        "Technocracy",
        mapOf(
            Category.ECONOMIC to 60.0,
            Category.DIPLOMATIC to 60.0,
            Category.CIVIL to 20.0,
            Category.SOCIETAL to 70.0
        )
    ),
    CENTRIST(
        "Centrist",
        mapOf(
            Category.ECONOMIC to 50.0,
            Category.DIPLOMATIC to 50.0,
            Category.CIVIL to 50.0,
            Category.SOCIETAL to 50.0
        )
    ),
    LIBERALISM(
        "Liberalism",
        mapOf(
            Category.ECONOMIC to 50.0,
            Category.DIPLOMATIC to 60.0,
            Category.CIVIL to 60.0,
            Category.SOCIETAL to 60.0
        )
    ),
    RELIGIOUS_ANARCHISM(
        "Religious Anarchism",
        mapOf(
            Category.ECONOMIC to 50.0,
            Category.DIPLOMATIC to 50.0,
            Category.CIVIL to 100.0,
            Category.SOCIETAL to 20.0
        )
    ),
    RIGHT_WING_POPULISM(
        "Right Wing Populism",
        mapOf(
            Category.ECONOMIC to 40.0,
            Category.DIPLOMATIC to 30.0,
            Category.CIVIL to 30.0,
            Category.SOCIETAL to 30.0
        )
    ),
    MODERATE_CONSERVATISM(
        "Moderate Conservatism",
        mapOf(
            Category.ECONOMIC to 40.0,
            Category.DIPLOMATIC to 40.0,
            Category.CIVIL to 50.0,
            Category.SOCIETAL to 30.0
        )
    ),
    REACTIONARY(
        "Reactionary",
        mapOf(
            Category.ECONOMIC to 40.0,
            Category.DIPLOMATIC to 40.0,
            Category.CIVIL to 40.0,
            Category.SOCIETAL to 10.0
        )
    ),
    SOCIAL_LIBERTARIANISM(
        "Social Libertarianism",
        mapOf(
            Category.ECONOMIC to 60.0,
            Category.DIPLOMATIC to 70.0,
            Category.CIVIL to 80.0,
            Category.SOCIETAL to 70.0
        )
    ),
    LIBERTARIANISM(
        "Libertarianism",
        mapOf(
            Category.ECONOMIC to 40.0,
            Category.DIPLOMATIC to 60.0,
            Category.CIVIL to 80.0,
            Category.SOCIETAL to 60.0
        )
    ),
    ANARCHO_EGOISM(
        "Anarcho-Egoism",
        mapOf(
            Category.ECONOMIC to 40.0,
            Category.DIPLOMATIC to 50.0,
            Category.CIVIL to 100.0,
            Category.SOCIETAL to 50.0
        )
    ),
    NAZISM(
        "Nazism",
        mapOf(
            Category.ECONOMIC to 40.0,
            Category.DIPLOMATIC to 0.0,
            Category.CIVIL to 0.0,
            Category.SOCIETAL to 5.0
        )
    ),
    AUTOCRACY(
        "Autocracy",
        mapOf(
            Category.ECONOMIC to 50.0,
            Category.DIPLOMATIC to 20.0,
            Category.CIVIL to 20.0,
            Category.SOCIETAL to 50.0
        )
    ),
    FASCISM(
        "Fascism",
        mapOf(
            Category.ECONOMIC to 40.0,
            Category.DIPLOMATIC to 20.0,
            Category.CIVIL to 20.0,
            Category.SOCIETAL to 20.0
        )
    ),
    CAPITALIST_FASCISM(
        "Capitalist Fascism",
        mapOf(
            Category.ECONOMIC to 20.0,
            Category.DIPLOMATIC to 20.0,
            Category.CIVIL to 20.0,
            Category.SOCIETAL to 20.0
        )
    ),
    CONSERVATISM(
        "Conservativism",
        mapOf(
            Category.ECONOMIC to 30.0,
            Category.DIPLOMATIC to 40.0,
            Category.CIVIL to 40.0,
            Category.SOCIETAL to 20.0
        )
    ),
    NEO_LIBERALISM(
        "Neo-Liberalism",
        mapOf(
            Category.ECONOMIC to 30.0,
            Category.DIPLOMATIC to 30.0,
            Category.CIVIL to 50.0,
            Category.SOCIETAL to 60.0
        )
    ),
    CLASSICAL_LIBERALISM(
        "Classical Liberalism",
        mapOf(
            Category.ECONOMIC to 30.0,
            Category.DIPLOMATIC to 60.0,
            Category.CIVIL to 60.0,
            Category.SOCIETAL to 80.0
        )
    ),
    AUTHORITARIAN_CAPITALISM(
        "Authoritarian Capitalism",
        mapOf(
            Category.ECONOMIC to 20.0,
            Category.DIPLOMATIC to 30.0,
            Category.CIVIL to 20.0,
            Category.SOCIETAL to 40.0
        )
    ),
    STATE_CAPITALISM(
        "State Capitalism",
        mapOf(
            Category.ECONOMIC to 20.0,
            Category.DIPLOMATIC to 50.0,
            Category.CIVIL to 30.0,
            Category.SOCIETAL to 50.0
        )
    ),
    NEO_CONSERVATISM(
        "Neo-Conservatism",
        mapOf(
            Category.ECONOMIC to 20.0,
            Category.DIPLOMATIC to 20.0,
            Category.CIVIL to 40.0,
            Category.SOCIETAL to 20.0
        )
    ),
    FUNDAMENTALISM(
        "Fundamentalism",
        mapOf(
            Category.ECONOMIC to 20.0,
            Category.DIPLOMATIC to 30.0,
            Category.CIVIL to 30.0,
            Category.SOCIETAL to 5.0
        )
    ),
    LIBERTARIAN_CAPITALISM(
        "Libertarian Capitalism",
        mapOf(
            Category.ECONOMIC to 20.0,
            Category.DIPLOMATIC to 50.0,
            Category.CIVIL to 80.0,
            Category.SOCIETAL to 60.0
        )
    ),
    MARKET_ANARCHISM(
        "Market Anarchism",
        mapOf(
            Category.ECONOMIC to 20.0,
            Category.DIPLOMATIC to 50.0,
            Category.CIVIL to 100.0,
            Category.SOCIETAL to 50.0
        )
    ),
    OBJECTIVISM(
        "Objectivism",
        mapOf(
            Category.ECONOMIC to 10.0,
            Category.DIPLOMATIC to 50.0,
            Category.CIVIL to 90.0,
            Category.SOCIETAL to 40.0
        )
    ),
    TOTALITARIAN_CAPITALISM(
        "Totalitarian Capitalism",
        mapOf(
            Category.ECONOMIC to 0.0,
            Category.DIPLOMATIC to 30.0,
            Category.CIVIL to 0.0,
            Category.SOCIETAL to 50.0
        )
    ),
    ULTRA_CAPITALISM(
        "Ultra-Capitalism",
        mapOf(
            Category.ECONOMIC to 0.0,
            Category.DIPLOMATIC to 40.0,
            Category.CIVIL to 50.0,
            Category.SOCIETAL to 50.0
        )
    ),
    ANARCHO_CAPITALISM(
        "Anarcho-Capitalism",
        mapOf(
            Category.ECONOMIC to 0.0,
            Category.DIPLOMATIC to 50.0,
            Category.CIVIL to 100.0,
            Category.SOCIETAL to 50.0
        )
    );

    override fun toString(): String = displayName

}
