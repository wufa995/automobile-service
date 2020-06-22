package cn.wufa995.automobile.handler;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.log4j.Logger;

@Slf4j
public class DateTypeHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        log.info("setParameter,Java类型参数转换");
        Date resultDate = null;
        try {
            resultDate = sdf.parse(parameter);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date sdate = new java.sql.Date(resultDate.getTime());
        ps.setDate(i, sdate);
    }
    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        log.info("getResult,Java类型参数转换,参数为：{}", columnName);
        return sdf.format(rs.getDate(columnName));
    }
    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }
    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }

}
