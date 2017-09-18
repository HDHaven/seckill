-- ���ݿ��ʼ���ű�

-- �������ݿ�
CREATE DATABASE DB_SECKILL;
-- ʹ�����ݿ�
USE DB_SECKILL;
-- ������ɱ����
CREATE TABLE TB_SECKILL (
	'SECKILL_ID' BIGINT NOT NULL AUTO_INCREMENT COMMENT '��Ʒ���id',
	'NAME' VARCHAR(120) NOT NULL COMMENT '��Ʒ����',
	'NUMBER' INT NOT NULL COMMENT '�������',
	'START_TIME' TIMESTAMP NOT NULL COMMENT '��ɱ��ʼʱ��',
	'END_TIME' TIMESTAMP NOT NULL COMMENT '��ɱ����ʱ��',
	'CREATE_TIME' TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
	PRIMARY KEY (SECKILL_ID),
	KEY IDX_START_TIME(START_TIME),	-- ����������������ѯ�ٶ�
	KEY IDX_END_TIME(END_TIME),
	KEY IDX_CREATE_TIME(CREATE_TIME)
)ENGINE=INNODB AUTO_INCREMENT=1000 DEFAULT CHARSET=UTF8 COMMENT='��ɱ����'

-- ��ʼ������
INSERT INTO 
	TB_SECKILL(NAME, NUMBER, START_TIME, END_TIME)
VALUES
	('1000Ԫ��ɱiPhone8', 100, '2017-09-01 00:00:00', '2017-09-02 00:00:00'),
	('500Ԫ��ɱipad3', 200, '2017-09-01 00:00:00', '2017-09-02 00:00:00'),
	('300Ԫ��ɱС��5', 500, '2017-09-01 00:00:00', '2017-09-02 00:00:00'),
	('200Ԫ��ɱ����note3', 1000, '2017-09-01 00:00:00', '2017-09-02 00:00:00');
	
-- ��ɱ�ɹ���ϸ��
-- �û���¼��֤�����Ϣ
CREATE TABLE TB_SUCCESS_KILLED (
	'SECKILL_ID' BIGINT NOT NULL COMMENT '��ɱ��ƷID',
	'USER_PHONE' BIGINT NOT NULL COMMENT '�û��ֻ���',
	'STATE' TINYINT NOT NULL DEFAULT -1 COMMENT '״̬��ʾ��-1����Ч	0���ɹ�	1���Ѹ���'
	'CREATE_TIME' TIMESTAMP NOT NULL COMMENT '����ʱ��',
	PRIMARY KEY(SECKILL_ID, USER_PHONE), /*��������*/
	KEY IDX_CREATE_TIME(CREATE_TIME)
)ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='��ɱ�ɹ���ϸ��'

