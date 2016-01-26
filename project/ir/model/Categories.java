package ir.model;

public enum Categories {
	RESTAURANTS("���������"),
	SHOP("����������"),
	TOURISM("�������"),
	FUN("����������"),
	CARS("����������"),
	RELAX("������� � ������"),
	CULTURE("�������"),
	HEALTH("������"),
	KIDS("�� ������"),
	SPORT("����� � ������"),
	LESSONS("����� � �������"),
	GAS_STATIONS("��������������"),
	OTHERS("�����"),
	DANCES("�����");

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
