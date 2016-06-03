SELECT q3.AutName
FROM(SELECT q2.Aname as AutName, q2.Adate
FROM  (SELECT auth.AUT_NAME as Aname , auth.AUT_BIRTHDATE as Adate
      FROM  (SELECT DISTINCT pa.AUTHOR_ID as autID
            FROM PUBLICATIONS pub, PUBLICATION_AUTHORS pa
            WHERE TO_NUMBER(SUBSTR(pub.P_DATE,1,4), '9999') = 2010 and pa.PUBLICATION_ID = pub.P_ID
            GROUP BY pa.AUTHOR_ID) q1, AUTHOR auth -- guy who published in 2010
      WHERE auth.AUT_ID = q1.autID and auth.AUT_BIRTHDATE IS NOT NULL and SUBSTR(auth.AUT_BIRTHDATE, 1, 4) != '0000'
      ORDER BY SUBSTR(auth.AUT_BIRTHDATE, 1,4) DESC)q2 -- year
WHERE ROWNUM < 1000
ORDER BY TO_NUMBER(SUBSTR(Adate, 1, 4), '9999') DESC, -- year 
         TO_NUMBER(SUBSTR(Adate, 6, 2), '99')   DESC, -- mounth 
         TO_NUMBER(SUBSTR(Adate, 9, 2), '99')   DESC  -- day
         ) q3
WHERE ROWNUM < 11

