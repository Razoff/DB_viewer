select AW_DATE, TO_NUMBER(SUBSTR(AW_DATE, 6, 2), '99'),TO_NUMBER(SUBSTR(AW_DATE, 3, 2), '99')
from AWARDS
where TO_NUMBER(SUBSTR(AW_DATE, 1, 4), '9999') = 2001 