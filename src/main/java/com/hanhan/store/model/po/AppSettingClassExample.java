package com.hanhan.store.model.po;

import java.util.ArrayList;
import java.util.List;

public class AppSettingClassExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AppSettingClassExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andLoadTypeIsNull() {
            addCriterion("load_type is null");
            return (Criteria) this;
        }

        public Criteria andLoadTypeIsNotNull() {
            addCriterion("load_type is not null");
            return (Criteria) this;
        }

        public Criteria andLoadTypeEqualTo(Integer value) {
            addCriterion("load_type =", value, "loadType");
            return (Criteria) this;
        }

        public Criteria andLoadTypeNotEqualTo(Integer value) {
            addCriterion("load_type <>", value, "loadType");
            return (Criteria) this;
        }

        public Criteria andLoadTypeGreaterThan(Integer value) {
            addCriterion("load_type >", value, "loadType");
            return (Criteria) this;
        }

        public Criteria andLoadTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("load_type >=", value, "loadType");
            return (Criteria) this;
        }

        public Criteria andLoadTypeLessThan(Integer value) {
            addCriterion("load_type <", value, "loadType");
            return (Criteria) this;
        }

        public Criteria andLoadTypeLessThanOrEqualTo(Integer value) {
            addCriterion("load_type <=", value, "loadType");
            return (Criteria) this;
        }

        public Criteria andLoadTypeIn(List<Integer> values) {
            addCriterion("load_type in", values, "loadType");
            return (Criteria) this;
        }

        public Criteria andLoadTypeNotIn(List<Integer> values) {
            addCriterion("load_type not in", values, "loadType");
            return (Criteria) this;
        }

        public Criteria andLoadTypeBetween(Integer value1, Integer value2) {
            addCriterion("load_type between", value1, value2, "loadType");
            return (Criteria) this;
        }

        public Criteria andLoadTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("load_type not between", value1, value2, "loadType");
            return (Criteria) this;
        }

        public Criteria andClassNameIsNull() {
            addCriterion("class_name is null");
            return (Criteria) this;
        }

        public Criteria andClassNameIsNotNull() {
            addCriterion("class_name is not null");
            return (Criteria) this;
        }

        public Criteria andClassNameEqualTo(String value) {
            addCriterion("class_name =", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotEqualTo(String value) {
            addCriterion("class_name <>", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameGreaterThan(String value) {
            addCriterion("class_name >", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameGreaterThanOrEqualTo(String value) {
            addCriterion("class_name >=", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameLessThan(String value) {
            addCriterion("class_name <", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameLessThanOrEqualTo(String value) {
            addCriterion("class_name <=", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameLike(String value) {
            addCriterion("class_name like", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotLike(String value) {
            addCriterion("class_name not like", value, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameIn(List<String> values) {
            addCriterion("class_name in", values, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotIn(List<String> values) {
            addCriterion("class_name not in", values, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameBetween(String value1, String value2) {
            addCriterion("class_name between", value1, value2, "className");
            return (Criteria) this;
        }

        public Criteria andClassNameNotBetween(String value1, String value2) {
            addCriterion("class_name not between", value1, value2, "className");
            return (Criteria) this;
        }

        public Criteria andContentMd5IsNull() {
            addCriterion("content_md5 is null");
            return (Criteria) this;
        }

        public Criteria andContentMd5IsNotNull() {
            addCriterion("content_md5 is not null");
            return (Criteria) this;
        }

        public Criteria andContentMd5EqualTo(String value) {
            addCriterion("content_md5 =", value, "contentMd5");
            return (Criteria) this;
        }

        public Criteria andContentMd5NotEqualTo(String value) {
            addCriterion("content_md5 <>", value, "contentMd5");
            return (Criteria) this;
        }

        public Criteria andContentMd5GreaterThan(String value) {
            addCriterion("content_md5 >", value, "contentMd5");
            return (Criteria) this;
        }

        public Criteria andContentMd5GreaterThanOrEqualTo(String value) {
            addCriterion("content_md5 >=", value, "contentMd5");
            return (Criteria) this;
        }

        public Criteria andContentMd5LessThan(String value) {
            addCriterion("content_md5 <", value, "contentMd5");
            return (Criteria) this;
        }

        public Criteria andContentMd5LessThanOrEqualTo(String value) {
            addCriterion("content_md5 <=", value, "contentMd5");
            return (Criteria) this;
        }

        public Criteria andContentMd5Like(String value) {
            addCriterion("content_md5 like", value, "contentMd5");
            return (Criteria) this;
        }

        public Criteria andContentMd5NotLike(String value) {
            addCriterion("content_md5 not like", value, "contentMd5");
            return (Criteria) this;
        }

        public Criteria andContentMd5In(List<String> values) {
            addCriterion("content_md5 in", values, "contentMd5");
            return (Criteria) this;
        }

        public Criteria andContentMd5NotIn(List<String> values) {
            addCriterion("content_md5 not in", values, "contentMd5");
            return (Criteria) this;
        }

        public Criteria andContentMd5Between(String value1, String value2) {
            addCriterion("content_md5 between", value1, value2, "contentMd5");
            return (Criteria) this;
        }

        public Criteria andContentMd5NotBetween(String value1, String value2) {
            addCriterion("content_md5 not between", value1, value2, "contentMd5");
            return (Criteria) this;
        }

        public Criteria andClassFileNameIsNull() {
            addCriterion("class_file_name is null");
            return (Criteria) this;
        }

        public Criteria andClassFileNameIsNotNull() {
            addCriterion("class_file_name is not null");
            return (Criteria) this;
        }

        public Criteria andClassFileNameEqualTo(String value) {
            addCriterion("class_file_name =", value, "classFileName");
            return (Criteria) this;
        }

        public Criteria andClassFileNameNotEqualTo(String value) {
            addCriterion("class_file_name <>", value, "classFileName");
            return (Criteria) this;
        }

        public Criteria andClassFileNameGreaterThan(String value) {
            addCriterion("class_file_name >", value, "classFileName");
            return (Criteria) this;
        }

        public Criteria andClassFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("class_file_name >=", value, "classFileName");
            return (Criteria) this;
        }

        public Criteria andClassFileNameLessThan(String value) {
            addCriterion("class_file_name <", value, "classFileName");
            return (Criteria) this;
        }

        public Criteria andClassFileNameLessThanOrEqualTo(String value) {
            addCriterion("class_file_name <=", value, "classFileName");
            return (Criteria) this;
        }

        public Criteria andClassFileNameLike(String value) {
            addCriterion("class_file_name like", value, "classFileName");
            return (Criteria) this;
        }

        public Criteria andClassFileNameNotLike(String value) {
            addCriterion("class_file_name not like", value, "classFileName");
            return (Criteria) this;
        }

        public Criteria andClassFileNameIn(List<String> values) {
            addCriterion("class_file_name in", values, "classFileName");
            return (Criteria) this;
        }

        public Criteria andClassFileNameNotIn(List<String> values) {
            addCriterion("class_file_name not in", values, "classFileName");
            return (Criteria) this;
        }

        public Criteria andClassFileNameBetween(String value1, String value2) {
            addCriterion("class_file_name between", value1, value2, "classFileName");
            return (Criteria) this;
        }

        public Criteria andClassFileNameNotBetween(String value1, String value2) {
            addCriterion("class_file_name not between", value1, value2, "classFileName");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}