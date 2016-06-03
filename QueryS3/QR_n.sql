SELECT aut.AUT_NAME
FROM  (SELECT DISTINCT pa.AUTHOR_ID as auth, sum(pub.PUBLICATION_PAGES) / sum(TO_NUMBER(SUBSTR(pub.PRICE,2,6)))
      FROM PUBLICATIONS pub, PUBLICATION_AUTHORS pa
      WHERE pub.P_ID = pa.PUBLICATION_ID and SUBSTR(pub.PRICE,1,1) = '$' -- gives distinct id number of pages and price ($)
      GROUP BY pa.AUTHOR_ID
      ORDER BY sum(pub.PUBLICATION_PAGES) / sum(TO_NUMBER(SUBSTR(pub.PRICE,2,6))) DESC) q1, AUTHOR aut
WHERE aut.AUT_ID = q1.auth and ROWNUM < 11