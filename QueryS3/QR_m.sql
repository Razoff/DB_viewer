SELECT la.LG_NAME, aut.AUT_NAME
FROM  (SELECT q2.lang as lang, pa.AUTHOR_ID as auth, count(pa.AUTHOR_ID),
              ROW_NUMBER () OVER (PARTITION BY q2.lang
                            ORDER BY count(pa.AUTHOR_ID)) as rec
      FROM  (SELECT q1.lang as lang, tii.T_ID as title
              FROM (SELECT DISTINCT LG_ID as lang
                    FROM LANGUAGES) q1, TITLE ti, TITLE tii
            WHERE ti.PARENT IS NOT NULL and tii.T_ID = ti.PARENT and tii.TYPE = 'NOVEL' and tii.LANGUAGE_ID = q1.lang) q2 -- all title translated and their langugae
      , PUBLICATION_CONTENT pc, PUBLICATION_AUTHORS pa
      WHERE pc.TITLE_ID = q2.title and pc.PUBLICATION_ID = pa.PUBLICATION_ID
      GROUP BY q2.lang, pa.AUTHOR_ID) q3, AUTHOR aut, LANGUAGES la
WHERE q3.rec <= 3 and aut.AUT_ID = q3.auth and la.LG_ID = q3.lang
ORDER BY q3.lang