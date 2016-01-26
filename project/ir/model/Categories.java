package ir.model;

public enum Categories {
	RESTAURANTS("Заведения"),
	SHOP("Пазаруване"),
	TOURISM("Туризъм"),
	FUN("Забавления"),
	CARS("Автомобили"),
	RELAX("Красота и Релакс"),
	CULTURE("Култура"),
	HEALTH("Здраве"),
	KIDS("За децата"),
	SPORT("Спорт и Фитнес"),
	LESSONS("Уроци и курсове"),
	GAS_STATIONS("Бензиностанции"),
	OTHERS("Други"),
	DANCES("Танци");

	private String name;

	Categories(String n) {
		name = n;
	}

	public String getName() {
		return name;
	}

	public static Categories getEnum(String value) {
		for (Categories v : values())
			if (v.getName().equalsIgnoreCase(value))
				return v;
		throw new IllegalArgumentException();
	}

	@Override
	public String toString() {
		return getName();
	}
}
