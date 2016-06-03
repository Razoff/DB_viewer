SELECT DISTINCT auth.AUT_NAME, auth.AUT_BIRTHDATE
FROM PUBLICATIONS pub, PUBLICATION_AUTHORS pa, AUTHOR auth
WHERE pub.P_ID = pa.PUBLICATION_ID and auth.AUT_ID = pa.AUTHOR_ID
      and auth.AUT_DEATHDATE IS NULL and pub.PUBLICATION_TYPE = 'ANTHOLOGY' and auth.AUT_BIRTHDATE IS NOT NULL
      -- non dead authors who wrote an anthology
ORDER BY TO_NUMBER(SUBSTR(auth.AUT_BIRTHDATE, 1, 4), '9999') DESC, -- year 
         TO_NUMBER(SUBSTR(auth.AUT_BIRTHDATE, 6, 2), '99')   DESC, -- mounth 
         TO_NUMBER(SUBSTR(auth.AUT_BIRTHDATE, 9, 2), '99')   DESC  -- day
