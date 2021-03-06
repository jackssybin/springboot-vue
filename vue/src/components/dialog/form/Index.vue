<template>
    <div id="dialogForm" style="overflow: hidden;">
        <el-form ref="form" size="mini" :label-width="labelWidth"
                 :model="formData || {}" :rules="formRules || {}">
            <slot name="itemHead"></slot>
            <el-form-item v-for="item in formOptions" :key="item.prop"
                          v-if="!item['hidden']"
                          :label='item.label' :prop="item.prop" :required="item.required || false">
                <template v-if="'input' === item.type">
                    <el-input v-model="formData[item.prop]" :size="item.size" :type="item.inputType || 'text'"
                              :placeholder="item.placeholder || item.label || '请输入'" clearable
                              :disabled="item.disabled || false"></el-input>
                </template>
                <template v-else-if="'select' === item.type">
                    <el-select v-model="formData[item.prop]" :size="item.size"
                               clearable :disabled="item.disabled || false"
                               :placeholder="item.placeholder || '请选择'"
                               @change="selectChange(formData[item.prop],item.prop)">
                        <el-option v-for="option in item.options" :key="option.value"
                                   :label="option.label" :value="option.value"
                                   :disabled="option.disabled || false"></el-option>
                    </el-select>
                </template>
                <template v-else-if="'cascader' === item.type">
                    <el-cascader v-model="formData[item.prop]" :size="item.size"
                                 clearable filterable :disabled="item.disabled || false"
                                 :placeholder="item.placeholder || '请选择'"
                                 :options="item.options || []"
                                 :props="item['props'] || {  checkStrictly: true }"
                                 @change="selectChange(formData[item.prop],item.prop)"></el-cascader>
                </template>
                <template v-else-if="'radio' === item.type">
                    <el-radio-group v-model="formData[item.prop]" :size="item.size"
                                    :disabled="item.disabled || false">
                        <el-radio v-for="option in item.options" :key="option.value"
                                  :label="option.value" :disabled="option.disabled || false">
                            {{option.label}}
                        </el-radio>
                    </el-radio-group>
                </template>
                <template v-else-if="'textarea' === item.type">
                    <el-input type="textarea" :size="item.size" v-model="formData[item.prop]"
                              :disabled="item.disabled || false" autosize></el-input>
                </template>
                <template v-else-if="'checkbox' === item.type">
                    <el-checkbox-group v-model="formData[item.prop]" :size="item.size"
                                       :disabled="item.disabled || false">
                        <el-checkbox v-for="option in options" :key="option.value"
                                     :label="option.value" :disabled="option.disabled || false">{{option.label}}
                        </el-checkbox>
                    </el-checkbox-group>
                </template>
                <template v-else-if="'datePicker' === item.type">
                    <el-date-picker type="date" :placeholder="item.placeholder || '选择日期'"
                                    v-model="formData[item.prop]" :size="item.size"
                                    :value-format="item.valueFormat || 'yyyy-MM-dd'"
                                    :disabled="item.disabled || false"></el-date-picker>
                </template>
                <template v-else-if="'timePicker' === item.type">
                    <el-date-picker :placeholder="item.placeholder || '选择时间'"
                                    v-model="formData[item.prop]" :size="item.size"
                                    :value-format="item.valueFormat || 'HH:mm:ss'"
                                    :disabled="item.disabled || false"></el-date-picker>
                </template>
                <template v-else-if="'dateTimePicker' === item.type">
                    <el-date-picker type="datetime" :placeholder="item.placeholder || '选择时间'"
                                    v-model="formData[item.prop]" :size="item.size"
                                    :value-format="item.valueFormat || 'yyyy-MM-dd HH:mm:ss'"
                                    :disabled="item.disabled || false"></el-date-picker>
                </template>
                <template v-else>
                    {{item.label}}没有指定type
                </template>
            </el-form-item>
            <slot name="itemTail"></slot>
        </el-form>
        <div slot="footer">
            <div style="float: right;">
                <el-button @click="$emit('closeDialog')">取 消</el-button>
                <slot name="button"></slot>
                <el-button type="warning" @click="resetForm">重置</el-button>
                <el-button type="primary" @click="submitForm">确 定</el-button>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Index",
        props: {
            //form表单
            formData: {
                type: Object,
                default: () => {
                }
            },
            //form表单规则
            formRules: {
                type: Object,
                default: () => {
                }
            },
            //form表单组件
            formOptions: {
                type: Array,
                default: () => []
            },
            //表单左边的宽度
            labelWidth: {
                type: String,
                default: '10rem'
            }
        },
        methods: {
            //下拉框值改变触发事件
            selectChange(value, prop) {
                this.$emit('selectChange', value, prop);
            },
            //监听表单提交
            submitForm() {
                let that = this;
                this.$refs['form'].validate((valid) => {
                    if (!valid) {
                        return false;
                    }
                    let form = {};
                    for (let key in that.formData) {
                        if (!that.formData.hasOwnProperty(key)) {
                            break;
                        }
                        for (let i = 0; i < that.formOptions.length; i++) {
                            let {prop, hidden} = that.formOptions[i];
                            //如果当前项隐藏
                            if (hidden) {
                                continue;
                            }
                            if ('id' === key || key === prop) {
                                form[key] = that.formData[key];
                                break;
                            }
                        }
                    }
                    that.$emit('submit', form);
                });
            },
            //重置表单
            resetForm() {
                this.$refs['form'].resetFields();
            }
        }
    }
</script>