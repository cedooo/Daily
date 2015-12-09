<!-- 信息系统报警总量 -->
	<select id="infoSysAlarmTotal" resultType="String" parameterType="int">
		SELECT 	SUM(abtc.nums+abtd.nums)
		FROM 
		(
			SELECT count(*) as nums, fSeverity
			FROM 
				(	
					SELECT tmct.MOSN   
					FROM(
						SELECT ctid, tcmo.MOSN AS appMosn
						FROM tcmo LEFT JOIN tcapct ON tcmo.MOSN=tcapct.MOSN
						WHERE  
								tcmo.fstatus=99 AND tcmo.fMOType LIKE 'ap%'
							AND tcapct.ctid IS NOT NULL
						)  abta 
						LEFT JOIN tmct ON abta.ctid=tmct.fCtID 
					UNION   
					SELECT DISTINCT mosn FROM tcmo WHERE  
								tcmo.fstatus=99 AND tcmo.fMOType LIKE 'ap%'     
				) abtb LEFT JOIN tfhis  ON abtb.MOSN=tfhis.MOSN
			WHERE 
				<if test="value==1">
					DATEDIFF(fLastTime,NOW())=-1
				</if>
				<if test="value==2">
					 YEARWEEK(fLastTime) = YEARWEEK(DATE_SUB(CURDATE(),INTERVAL 1 WEEK))
				</if>
				<if test="value==3">
					YEAR(fLastTime) = YEAR(DATE_SUB(CURDATE(),INTERVAL 1 MONTH))
					MONTH(fLastTime) = MONTH(DATE_SUB(CURDATE(),INTERVAL 1 MONTH))
				</if>
			GROUP BY fSeverity
		) abtc JOIN 
		(
			SELECT count(*) as nums, fSeverity
			FROM 
				(	
					SELECT tmct.MOSN  
					FROM(
						SELECT ctid, tcmo.MOSN AS appMosn
						FROM tcmo LEFT JOIN tcapct ON tcmo.MOSN=tcapct.MOSN
						WHERE  
								tcmo.fstatus=99 AND tcmo.fMOType LIKE 'ap%' 
							AND tcapct.ctid IS NOT NULL
						)  abta LEFT JOIN tmct ON abta.ctid=tmct.fCtID 
					UNION   
					SELECT DISTINCT mosn FROM tcmo WHERE 
								tcmo.fstatus=99 AND tcmo.fMOType LIKE 'ap%'    
				) abtb	LEFT JOIN tfactive  ON abtb.MOSN=tfactive.MOSN
			WHERE 
				<if test="value==1">
					DATEDIFF(fLastTime,NOW())=-1
				</if>
				<if test="value==2">
					 YEARWEEK(fLastTime) = YEARWEEK(DATE_SUB(CURDATE(),INTERVAL 1 WEEK))
				</if>
				<if test="value==3">
					YEAR(fLastTime) = YEAR(DATE_SUB(CURDATE(),INTERVAL 1 MONTH))
					MONTH(fLastTime) = MONTH(DATE_SUB(CURDATE(),INTERVAL 1 MONTH))
				</if>
			GROUP BY fSeverity
		)abtd 
		ON abtc.fSeverity  = abtd.fSeverity
	</select>	
	<!-- 一般报警 -->
	<select id="infoSysAlarmCommon" resultType="String" parameterType="int">
		SELECT 	abtc.nums+abtd.nums as total
		FROM 
		(
			SELECT count(*) as nums, fSeverity
			FROM 
				(	
					SELECT tmct.MOSN   
					FROM(
						SELECT ctid, tcmo.MOSN AS appMosn
						FROM tcmo LEFT JOIN tcapct ON tcmo.MOSN=tcapct.MOSN
						WHERE  
								tcmo.fstatus=99 AND tcmo.fMOType LIKE 'ap%'
							AND tcapct.ctid IS NOT NULL
						)  abta 
						LEFT JOIN tmct ON abta.ctid=tmct.fCtID 
					UNION   
					SELECT DISTINCT mosn FROM tcmo WHERE  
								tcmo.fstatus=99 AND tcmo.fMOType LIKE 'ap%'     
				) abtb	
						LEFT JOIN tfhis  ON abtb.MOSN=tfhis.MOSN
			WHERE fSeverity = 2 
				<if test="value==1">
					AND DATEDIFF(fLastTime,NOW())=-1
				</if>
				<if test="value==2">
					AND YEARWEEK(fLastTime) = YEARWEEK(DATE_SUB(CURDATE(),INTERVAL 1 WEEK))
				</if>
				<if test="value==3">
					AND YEAR(fLastTime) = YEAR(DATE_SUB(CURDATE(),INTERVAL 1 MONTH))
					AND MONTH(fLastTime) = MONTH(DATE_SUB(CURDATE(),INTERVAL 1 MONTH))
				</if>
			GROUP BY fSeverity
		) abtc JOIN 
		(
			SELECT count(*) as nums, fSeverity
			FROM 
				(	
					SELECT tmct.MOSN  
					FROM(
						SELECT ctid, tcmo.MOSN AS appMosn
						FROM tcmo LEFT JOIN tcapct ON tcmo.MOSN=tcapct.MOSN
						WHERE  
								tcmo.fstatus=99 AND tcmo.fMOType LIKE 'ap%' 
							AND tcapct.ctid IS NOT NULL
						)  abta 
						LEFT JOIN tmct ON abta.ctid=tmct.fCtID 
					UNION   
					SELECT DISTINCT mosn FROM tcmo WHERE 
								tcmo.fstatus=99 AND tcmo.fMOType LIKE 'ap%'    
				) abtb	
						LEFT JOIN tfactive  ON abtb.MOSN=tfactive.MOSN
			WHERE fSeverity = 2 
				<if test="value==1">
					AND DATEDIFF(fLastTime,NOW())=-1
				</if>
				<if test="value==2">
					AND YEARWEEK(fLastTime) = YEARWEEK(DATE_SUB(CURDATE(),INTERVAL 1 WEEK))
				</if>
				<if test="value==3">
					AND YEAR(fLastTime) = YEAR(DATE_SUB(CURDATE(),INTERVAL 1 MONTH))
					AND MONTH(fLastTime) = MONTH(DATE_SUB(CURDATE(),INTERVAL 1 MONTH))
				</if> 
			GROUP BY fSeverity
		)abtd 
		ON abtc.fSeverity  = abtd.fSeverity
	</select>	