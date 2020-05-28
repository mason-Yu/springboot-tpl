// Create your own language mapping
// Any keys not defined here will fall back to the "en" language
JSONEditor.defaults.languages.zh_CN = {
  /**
   * When a property is not set
   */
  error_notset: "Property must be set",
  /**
   * When a string must not be empty
   */
  error_notempty: "值不能为空，需要填写",
  /**
   * When a value is not one of the enumerated values
   */
  error_enum: "Value must be one of the enumerated values",
  /**
   * When a value doesn't validate any schema of a 'anyOf' combination
   */
  error_anyOf: "Value must validate against at least one of the provided schemas",
  /**
   * When a value doesn't validate
   * @variables This key takes one variable: The number of schemas the value does not validate
   */
  error_oneOf: 'Value must validate against exactly one of the provided schemas. It currently validates against {{0}} of the schemas.',
  /**
   * When a value does not validate a 'not' schema
   */
  error_not: "Value must not validate against the provided schema",
  /**
   * When a value does not match any of the provided types
   */
  error_type_union: "Value must be one of the provided types",
  /**
   * When a value does not match the given type
   * @variables This key takes one variable: The type the value should be of
   */
  error_type: "Value must be of type {{0}}",
  /**
   *  When the value validates one of the disallowed types
   */
  error_disallow_union: "Value must not be one of the provided disallowed types",
  /**
   *  When the value validates a disallowed type
   * @variables This key takes one variable: The type the value should not be of
   */
  error_disallow: "Value must not be of type {{0}}",
  /**
   * When a value is not a multiple of or divisible by a given number
   * @variables This key takes one variable: The number mentioned above
   */
  error_multipleOf: "Value must be a multiple of {{0}}",
  /**
   * When a value is greater than it's supposed to be (exclusive)
   * @variables This key takes one variable: The maximum
   */
  error_maximum_excl: "Value must be less than {{0}}",
  /**
   * When a value is greater than it's supposed to be (inclusive
   * @variables This key takes one variable: The maximum
   */
  error_maximum_incl: "Value must be at most {{0}}",
  /**
   * When a value is lesser than it's supposed to be (exclusive)
   * @variables This key takes one variable: The minimum
   */
  error_minimum_excl: "Value must be greater than {{0}}",
  /**
   * When a value is lesser than it's supposed to be (inclusive)
   * @variables This key takes one variable: The minimum
   */
  error_minimum_incl: "Value must be at least {{0}}",
  /**
   * When a value have too many characters
   * @variables This key takes one variable: The maximum character count
   */
  error_maxLength: "Value must be at most {{0}} characters long",
  /**
   * When a value does not have enough characters
   * @variables This key takes one variable: The minimum character count
   */
  error_minLength: "Value must be at least {{0}} characters long",
  /**
   * When a value does not match a given pattern
   */
  error_pattern: "Value must match the pattern {{0}}",
  /**
   * When an array has additional items whereas it is not supposed to
   */
  error_additionalItems: "No additional items allowed in this array",
  /**
   * When there are to many items in an array
   * @variables This key takes one variable: The maximum item count
   */
  error_maxItems: "Value must have at most {{0}} items",
  /**
   * When there are not enough items in an array
   * @variables This key takes one variable: The minimum item count
   */
  error_minItems: "Value must have at least {{0}} items",
  /**
   * When an array is supposed to have unique items but has duplicates
   */
  error_uniqueItems: "Array must have unique items",
  /**
   * When there are too many properties in an object
   * @variables This key takes one variable: The maximum property count
   */
  error_maxProperties: "Object must have at most {{0}} properties",
  /**
   * When there are not enough properties in an object
   * @variables This key takes one variable: The minimum property count
   */
  error_minProperties: "Object must have at least {{0}} properties",
  /**
   * When a required property is not defined
   * @variables This key takes one variable: The name of the missing property
   */
  error_required: "Object is missing the required property '{{0}}'",
  /**
   * When there is an additional property is set whereas there should be none
   * @variables This key takes one variable: The name of the additional property
   */
  error_additional_properties: "No additional properties allowed, but property {{0}} is set",
  /**
   * When a dependency is not resolved
   * @variables This key takes one variable: The name of the missing property for the dependency
   */
  error_dependency: "Must have property {{0}}",
  /**
   * When a date is in incorrect format
   * @variables This key takes one variable: The valid format
   */
  error_date: 'Date must be in the format {{0}}',
  /**
   * When a time is in incorrect format
   * @variables This key takes one variable: The valid format
   */
  error_time: 'Time must be in the format {{0}}',
  /**
   * When a datetime-local is in incorrect format
   * @variables This key takes one variable: The valid format
   */
  error_datetime_local: 'Datetime must be in the format {{0}}',
  /**
   * When a integer date is less than 1 January 1970
   */
  error_invalid_epoch: 'Date must be greater than 1 January 1970',

  /**
   * Text on Delete All buttons
   */
  button_delete_all: "所有",
  /**
   * Title on Delete All buttons
   */
  button_delete_all_title: "删除所有",
  /**
    * Text on Delete Last buttons
    * @variable This key takes one variable: The title of object to delete
    */
  button_delete_last: "最后 {{0}}",
  /**
    * Title on Delete Last buttons
    * @variable This key takes one variable: The title of object to delete
    */
  button_delete_last_title: "删除 最后 {{0}}",
  /**
    * Title on Add Row buttons
    * @variable This key takes one variable: The title of object to add
    */
  button_add_row_title: "添加 {{0}}",
  /**
    * Title on Move Down buttons
    */
  button_move_down_title: "下移",
  /**
    * Title on Move Up buttons
    */
  button_move_up_title: "上移",
  /**
    * Title on Delete Row buttons
    * @variable This key takes one variable: The title of object to delete
    */
  button_delete_row_title: "删除 {{0}}",
  /**
    * Title on Delete Row buttons, short version (no parameter with the object title)
    */
  button_delete_row_title_short: "删除",
  /**
    * Title on Collapse buttons
    */
  button_collapse: "收起",
  /**
    * Title on Expand buttons
    */
  button_expand: "展开",
  /**
    * Title on Flatpickr toggle buttons
    */
  flatpickr_toggle_button: "切换",
  /**
    * Title on Flatpickr clear buttons
    */
  flatpickr_clear_button: "清空"
};

JSONEditor.defaults.language = "zh_CN";