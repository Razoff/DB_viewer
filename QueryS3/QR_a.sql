SELECT q3.dollar, ROUND(AVG(TO_NUMBER(SUBSTR(pub.PRICE,3,6))),2) as pound
FROM  (SELECT ROUND(AVG(TO_NUMBER(SUBSTR(pub.PRICE,2,6))),2) as dollar, q2.titID as titID
        FROM  (SELECT q1.titID as titID
              FROM  (SELECT TITLE_ID as titID, COUNT(PUBLICATION_ID)
                    FROM PUBLICATION_CONTENT
                    GROUP BY TITLE_ID
                    ORDER BY COUNT(PUBLICATION_ID) DESC)q1
              WHERE ROWNUM < 2) q2 , PUBLICATION_CONTENT pc, PUBLICATIONS pub
       WHERE q2.titID = pc.TITLE_ID and pc.PUBLICATION_ID = pub.P_ID and SUBSTR(pub.PRICE,1,1) = '$'
       GROUP BY q2.titID)q3 , PUBLICATION_CONTENT pc, PUBLICATIONS pub
WHERE q3.titID = pc.TITLE_ID and pc.PUBLICATION_ID = pub.P_ID and SUBSTR(pub.PRICE,1,1) = '£' 
GROUP BY q3.dollar
-- 14.04$ , 5.73£
      