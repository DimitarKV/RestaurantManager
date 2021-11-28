export function valid(field) {
    return !(field.value.length < 4 || field.value.length > 20);
}

export function validate(field, optional = false, validator = valid){
    if(optional){
        if (field.value.trim().length !== 0 && !validator(field)) {
            field.classList.remove("is-valid");
            field.classList.add("is-invalid");
            return false;
        } else {
            field.classList.remove("is-invalid");
            field.classList.add("is-valid");

        }
    } else {
        if (!validator(field)) {
            field.classList.remove("is-valid");
            field.classList.add("is-invalid");
            return false;
        } else {
            field.classList.remove("is-invalid");
            field.classList.add("is-valid");
        }
    }
    return true;
}