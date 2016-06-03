SELECT auth.AUT_NAME
FROM (SELECT q1.autID as authh, count(q1.titID) as co
      FROM (SELECT DISTINCT pa.AUTHOR_ID as autID, pc.TITLE_ID as titID
            FROM PUBLICATION_AUTHORS pa, PUBLICATION_CONTENT pc
            WHERE pc.PUBLICATION_ID = pa.PUBLICATION_ID) q1, TITLE_TAGS tt, TAGS tag
      WHERE q1.titID = tt.TITLE_ID and tt.TAG_ID = tag.TG_ID --and tag.TG_NAME = 'science fiction'
      GROUP BY q1.autID
      ORDER BY count(q1.titID) DESC) q2, AUTHOR auth
WHERE ROWNUM < 2 and q2.authh = auth.AUT_ID
