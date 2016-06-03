SELECT q2.dates, ROUND(AVG(nb), 2) as Average
FROM  (SELECT DISTINCT q1.dates as dates , pub.PUBLISHER_ID , COUNT(pa.AUTHOR_ID) as  nb
      FROM  
        (SELECT DISTINCT TO_NUMBER(SUBSTR(P_DATE, 1, 4), '9999') as dates
        FROM PUBLICATIONS) q1, PUBLICATIONS pub, PUBLICATION_AUTHORS pa
      WHERE TO_NUMBER(SUBSTR(pub.P_DATE, 1, 4), '9999') = q1.dates and pub.P_ID = pa.PUBLICATION_ID
      GROUP BY pub.PUBLISHER_ID , q1.dates) q2
GROUP BY q2.dates
ORDER BY q2.dates ASC