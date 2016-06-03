SELECT ps.P_S_NAME
FROM  (SELECT pub.PUBLICATION_SERIES_ID as pnnb, count(pub.PUBLICATION_SERIES_ID) -- the publication series number of everyone that won the price
      FROM  (SELECT DISTINCT taw.TITLE_ID as title -- query gives all title that wins this award
            FROM TITLE tit, TITLE_AWARD taw, AWARDS aw, AWARD_TYPES awt
            WHERE tit.T_ID = taw.TITLE_ID and taw.AWARD_ID = aw.AW_ID and aw.AW_TYPE_ID = awt.AW_T_ID and awt.AW_T_NAME = 'World Fantasy Award') q1
      , PUBLICATION_CONTENT pc, PUBLICATIONS pub
      WHERE q1.title = pc.TITLE_ID and pub.P_ID = pc.PUBLICATION_ID and pub.PUBLICATION_SERIES_ID IS NOT NULL
      GROUP BY pub.PUBLICATION_SERIES_ID
      ORDER BY count(pub.PUBLICATION_SERIES_ID) DESC) q2, P_SERIES ps
WHERE ROWNUM < 2 and ps.P_S_ID = pnnb -- keep only the best