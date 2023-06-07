CREATE PROCEDURE SLOTS
AS
BEGIN
    DECLARE @startDate DATE;
    DECLARE @endDate DATE;
    DECLARE @doctorId INT;
    DECLARE @date DATE;
    SET @startDate = GETDATE();
    SET @endDate = DATEADD(DAY, 10, @startDate);
    SET @doctorId = 1;

    WHILE @doctorId <= 31
    BEGIN
        SET @date = @startDate;
        WHILE @date <= @endDate
        BEGIN
            INSERT INTO APPOINTMENT_SLOTS (DOCTOR_ID, [DATE], [TIME], AVAILABLE)
            VALUES 
            (@doctorId, @date, '10:00:00', 1),
            (@doctorId, @date, '12:00:00', 1),
            (@doctorId, @date, '14:00:00', 1),
            (@doctorId, @date, '18:00:00', 1),
            (@doctorId, @date, '20:00:00', 1),
            (@doctorId, @date, '22:00:00', 1);

            SET @date = DATEADD(DAY, 1, @date);
        END

        SET @doctorId = @doctorId + 1;
    END
END
