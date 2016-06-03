SELECT auth.AUT_NAME, ac.AW_C_NAME
FROM (SELECT pa.AUTHOR_ID as aut , aw.AW_CAT_ID as cat, count(aw.AW_CAT_ID),
      ROW_NUMBER () OVER (PARTITION BY aw.AW_CAT_ID
                           ORDER BY count(aw.AW_CAT_ID)) as rec
      FROM AWARDS aw,TITLE_AWARD ta, TITLE tit, PUBLICATION_CONTENT pc, PUBLICATIONS pub, PUBLICATION_AUTHORS pa
      WHERE aw.AW_ID = ta.AWARD_ID and ta.TITLE_ID = tit.T_ID and tit.T_ID = pc.TITLE_ID and pc.PUBLICATION_ID = pub.P_ID and pub.P_ID = pa.PUBLICATION_ID
      GROUP BY pa.AUTHOR_ID, aw.AW_CAT_ID) q1, AUTHOR auth, AWARD_CATEGORIES ac
WHERE rec <= 3 and auth.AUT_ID = q1.aut and q1.cat = ac.AW_C_ID
order by q1.cat
