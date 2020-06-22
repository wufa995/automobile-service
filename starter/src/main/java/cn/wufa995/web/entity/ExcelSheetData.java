package cn.wufa995.web.entity;

import cn.wufa995.common.enums.ExcelType;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelSheetData {
    private Class clazz;
    private String title = "导出数据";
    private List<Map<String, String>> headers = new ArrayList();
    private List datas = new ArrayList();
    private ExcelType excelType;
    private boolean hasIndexColumn;
    private boolean isTrim;
    private String groupField;
    private int index;
    private boolean isMap = false;


    public void addHeader(String field, String title) {
        this.headers.add(this.createHeader(field, title));
    }

    public void addHeader(String field, String title, int width) {
        this.headers.add(this.createHeader(field, title, width));
    }

    public void addHeader(String field, String title, int width, boolean center) {
        this.headers.add(this.createHeader(field, title, width, center));
    }

    public Map createHeader(String field, String title) {
        return this.createHeader(field, title, 15);
    }

    public Map createHeader(String field, String title, Integer width) {
        return this.createHeader(field, title, width, false);
    }

    public Map createHeader(String field, String title, Integer width, Boolean center) {
        width = width * 256;
        Map<String, String> header = new HashMap(3);
        header.put("field", field);
        header.put("title", title);
        header.put("width", width.toString());
        header.put("center", center.toString());
        return header;
    }

    public void setData(List data) {
        this.datas = data;
    }

    public List getData() {
        return this.datas;
    }

    public void setIsMap(Boolean isMap) {
        this.isMap = isMap;
    }

    public Boolean getIsMap() {
        return this.isMap;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setExcelType(ExcelType excelType) {
        this.excelType = excelType;
    }

    public void setGroupField(String groupField) {
        this.groupField = groupField;
    }

    public void setIsTrim(boolean isTrim) {
        this.isTrim = isTrim;
    }

    public boolean isHasIndexColumn() {
        return this.hasIndexColumn;
    }

    public void setHasIndexColumn(boolean hasIndexColumn) {
        this.hasIndexColumn = hasIndexColumn;
    }

    public static ExcelSheetData.ExcelSheetDataBuilder builder() {
        return new ExcelSheetData.ExcelSheetDataBuilder();
    }

    public Class getClazz() {
        return this.clazz;
    }

    public List<Map<String, String>> getHeaders() {
        return this.headers;
    }

    public List getDatas() {
        return this.datas;
    }

    public ExcelType getExcelType() {
        return this.excelType;
    }

    public boolean isTrim() {
        return this.isTrim;
    }

    public String getGroupField() {
        return this.groupField;
    }

    public int getIndex() {
        return this.index;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public void setHeaders(List<Map<String, String>> headers) {
        this.headers = headers;
    }

    public void setDatas(List datas) {
        this.datas = datas;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ExcelSheetData)) {
            return false;
        } else {
            ExcelSheetData other = (ExcelSheetData)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label95: {
                    Object this$clazz = this.getClazz();
                    Object other$clazz = other.getClazz();
                    if (this$clazz == null) {
                        if (other$clazz == null) {
                            break label95;
                        }
                    } else if (this$clazz.equals(other$clazz)) {
                        break label95;
                    }

                    return false;
                }

                Object this$title = this.getTitle();
                Object other$title = other.getTitle();
                if (this$title == null) {
                    if (other$title != null) {
                        return false;
                    }
                } else if (!this$title.equals(other$title)) {
                    return false;
                }

                Object this$headers = this.getHeaders();
                Object other$headers = other.getHeaders();
                if (this$headers == null) {
                    if (other$headers != null) {
                        return false;
                    }
                } else if (!this$headers.equals(other$headers)) {
                    return false;
                }

                label74: {
                    Object this$datas = this.getDatas();
                    Object other$datas = other.getDatas();
                    if (this$datas == null) {
                        if (other$datas == null) {
                            break label74;
                        }
                    } else if (this$datas.equals(other$datas)) {
                        break label74;
                    }

                    return false;
                }

                label67: {
                    Object this$excelType = this.getExcelType();
                    Object other$excelType = other.getExcelType();
                    if (this$excelType == null) {
                        if (other$excelType == null) {
                            break label67;
                        }
                    } else if (this$excelType.equals(other$excelType)) {
                        break label67;
                    }

                    return false;
                }

                if (this.isHasIndexColumn() != other.isHasIndexColumn()) {
                    return false;
                } else if (this.isTrim() != other.isTrim()) {
                    return false;
                } else {
                    label57: {
                        Object this$groupField = this.getGroupField();
                        Object other$groupField = other.getGroupField();
                        if (this$groupField == null) {
                            if (other$groupField == null) {
                                break label57;
                            }
                        } else if (this$groupField.equals(other$groupField)) {
                            break label57;
                        }

                        return false;
                    }

                    if (this.getIndex() != other.getIndex()) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ExcelSheetData;
    }

    @Override
    public int hashCode() {
        int result = 1;
        Object $clazz = this.getClazz();
        result = result * 59 + ($clazz == null ? 43 : $clazz.hashCode());
        Object $title = this.getTitle();
        result = result * 59 + ($title == null ? 43 : $title.hashCode());
        Object $headers = this.getHeaders();
        result = result * 59 + ($headers == null ? 43 : $headers.hashCode());
        Object $datas = this.getDatas();
        result = result * 59 + ($datas == null ? 43 : $datas.hashCode());
        Object $excelType = this.getExcelType();
        result = result * 59 + ($excelType == null ? 43 : $excelType.hashCode());
        result = result * 59 + (this.isHasIndexColumn() ? 79 : 97);
        result = result * 59 + (this.isTrim() ? 79 : 97);
        Object $groupField = this.getGroupField();
        result = result * 59 + ($groupField == null ? 43 : $groupField.hashCode());
        result = result * 59 + this.getIndex();
        return result;
    }

    @Override
    public String toString() {
        return "ExcelSheetData(clazz=" + this.getClazz() + ", title=" + this.getTitle() + ", headers=" + this.getHeaders() + ", datas=" + this.getDatas() + ", excelType=" + this.getExcelType() + ", hasIndexColumn=" + this.isHasIndexColumn() + ", isTrim=" + this.isTrim() + ", groupField=" + this.getGroupField() + ", index=" + this.getIndex() + ")";
    }

    public ExcelSheetData() {
        this.excelType = ExcelType.NORMAL;
        this.hasIndexColumn = false;
        this.isTrim = false;
        this.groupField = "";
        this.index = 0;
    }

    @ConstructorProperties({"clazz", "title", "headers", "datas", "excelType", "hasIndexColumn", "isTrim", "groupField", "index"})
    public ExcelSheetData(Class clazz, String title, List<Map<String, String>> headers, List datas, ExcelType excelType, boolean hasIndexColumn, boolean isTrim, String groupField, int index) {
        this.excelType = ExcelType.NORMAL;
        this.hasIndexColumn = false;
        this.isTrim = false;
        this.groupField = "";
        this.index = 0;
        this.clazz = clazz;
        this.title = title;
        this.headers = headers;
        this.datas = datas;
        this.excelType = excelType;
        this.hasIndexColumn = hasIndexColumn;
        this.isTrim = isTrim;
        this.groupField = groupField;
        this.index = index;
    }

    public static class ExcelSheetDataBuilder {
        private Class clazz;
        private String title;
        private List<Map<String, String>> headers;
        private List datas;
        private ExcelType excelType;
        private boolean hasIndexColumn;
        private boolean isTrim;
        private String groupField;
        private int index;

        ExcelSheetDataBuilder() {
        }

        public ExcelSheetData.ExcelSheetDataBuilder clazz(Class clazz) {
            this.clazz = clazz;
            return this;
        }

        public ExcelSheetData.ExcelSheetDataBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ExcelSheetData.ExcelSheetDataBuilder headers(List<Map<String, String>> headers) {
            this.headers = headers;
            return this;
        }

        public ExcelSheetData.ExcelSheetDataBuilder datas(List datas) {
            this.datas = datas;
            return this;
        }

        public ExcelSheetData.ExcelSheetDataBuilder excelType(ExcelType excelType) {
            this.excelType = excelType;
            return this;
        }

        public ExcelSheetData.ExcelSheetDataBuilder hasIndexColumn(boolean hasIndexColumn) {
            this.hasIndexColumn = hasIndexColumn;
            return this;
        }

        public ExcelSheetData.ExcelSheetDataBuilder isTrim(boolean isTrim) {
            this.isTrim = isTrim;
            return this;
        }

        public ExcelSheetData.ExcelSheetDataBuilder groupField(String groupField) {
            this.groupField = groupField;
            return this;
        }

        public ExcelSheetData.ExcelSheetDataBuilder index(int index) {
            this.index = index;
            return this;
        }

        public ExcelSheetData build() {
            return new ExcelSheetData(this.clazz, this.title, this.headers, this.datas, this.excelType, this.hasIndexColumn, this.isTrim, this.groupField, this.index);
        }

        public String toString() {
            return "ExcelSheetData.ExcelSheetDataBuilder(clazz=" + this.clazz + ", title=" + this.title + ", headers=" + this.headers + ", datas=" + this.datas + ", excelType=" + this.excelType + ", hasIndexColumn=" + this.hasIndexColumn + ", isTrim=" + this.isTrim + ", groupField=" + this.groupField + ", index=" + this.index + ")";
        }
    }
}
