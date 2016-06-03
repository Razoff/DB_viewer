SELECT publ.P_TITLE
FROM  (SELECT q3.publication as pub, count(q3.webID)
      FROM  (SELECT DISTINCT q2.pID as publication, web.WEB_ID as webID
            FROM  (SELECT pub.P_ID as pID, pa.AUTHOR_ID as autID, pub.PUBLISHER_ID as pubID, pub.PUBLICATION_SERIES_ID as pubSID, ti.SERIES_ID as tisID
                  FROM  (SELECT DISTINCT pc.PUBLICATION_ID as pubID
                        FROM AWARD_TYPES at , AWARDS aw, TITLE_AWARD taw, PUBLICATION_CONTENT pc
                        WHERE at.AW_T_NAME = 'Nebula Award' and aw.AW_TYPE_ID = at.AW_T_ID and taw.AWARD_ID = aw.AW_ID and pc.TITLE_ID = taw.TITLE_ID) q1 -- publication that won a nebula award
                  ,PUBLICATIONS pub, PUBLICATION_AUTHORS pa, PUBLICATION_CONTENT pc, TITLE ti
                  WHERE pub.P_ID = q1.pubID and pub.P_ID = pa.PUBLICATION_ID and pc.PUBLICATION_ID = pub.P_ID and ti.T_ID = pc.TITLE_ID) q2
                  -- get for all publcations id his author id publisher id publication series id title series id
                  , WEBPAGES web
            WHERE q2.autID = web.AUTHOR_ID or q2.pubID = web.PUBLISHER_ID or q2.pubSID = web.PUBLICATION_SERIES_ID or q2.tisID = web.TITLE_ID) q3 -- get all pid and website associate
      GROUP BY q3.publication
      ORDER BY COUNT(q3.webID) DESC) q4, PUBLICATIONS publ
WHERE ROWNUM < 11 and publ.P_ID = q4.pub -- keep 10 better