"""
Quick & dirty date class with no validation.
The only useful thing here is the logic of day increment.
"""


class Date:
    def __init__(self, day: int, month: int, year: int):
        self._day = day
        self._month = month
        self._year = year

    def __str__(self) -> str:
        return (f'{str(self._day).zfill(2)}.'
                f'{str(self._month).zfill(2)}.'
                f'{str(self._year).zfill(4)}')

    def next(self) -> __init__:
        leap = (self._year % 4 == 0
                and not (self._year % 100 == 0 and self._year % 400 != 0))
        if self._day == 31 and self._month == 12:
            return Date(1, 1, self._year + 1)
        elif self._day == 31 and self._month in (1, 3, 5, 7, 8, 10):
            return Date(1, self._month + 1, self._year)
        elif self._day == 30 and self._month in (4, 6, 9, 11):
            return Date(1, self._month + 1, self._year)
        elif leap and self._month == 2 and self._day == 28:
            return Date(29, 2, self._year)
        elif leap and self._month == 2 and self._day == 29:
            return Date(1, 3, self._year)
        elif self._day == 28 and self._month == 2:
            return Date(1, 3, self._year)
        else:
            return Date(self._day + 1, self._month, self._year)


day = int(input('Day: '))
month = int(input('Month: '))
year = int(input('Year: '))

date = Date(day, month, year)
print(date.next())
