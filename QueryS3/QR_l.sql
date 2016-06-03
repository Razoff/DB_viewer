SELECT aut.AUT_NAME
FROM  (SELECT pa.AUTHOR_ID as auth, count(pa.AUTHOR_ID)
      FROM  (SELECT DISTINCT pc.PUBLICATION_ID as publica
            FROM REVIEW rw, PUBLICATION_CONTENT pc
            WHERE rw.REVIEW_ID = pc.TITLE_ID) q1 -- get all publication ID that are review
      , PUBLICATION_AUTHORS pa
      WHERE q1.publica = pa.PUBLICATION_ID
      GROUP BY pa.AUTHOR_ID
      ORDER BY count(pa.AUTHOR_ID) DESC) q2 -- get the number of said publication related to their author
, AUTHOR aut
WHERE ROWNUM < 2 and q2.auth = aut.AUT_ID -- keep best and get name