//
// Created by 0x41c on 2022-06-30.
//

#include <macOS/lookup/mh_functions.h>
#include <ptrauth.h>

void c_entry(void)
{
    typedef void *(*dlopen_t)(const char *, int);
    dlopen_t *$dlopen;

    /* We can't call dyld`dlopen when dyld3 is being used, so we must find libdyld`dlopen and call that instead */
    $dlopen = get_symbol_from_header(get_header_by_path("/usr/lib/system/libdyld.dylib"), "_dlopen");
    $dlopen = ptrauth_sign_unauthenticated($dlopen, ptrauth_key_function_pointer, 0);


    if ($dlopen) (*$dlopen)(argument, RTLD_NOW);
}

__attribute__((naked))
void entry(void)
{
    __asm__(
            "stp  x0,  x1, [sp,#-16]!   \n"
            "stp  x2,  x3, [sp,#-16]!   \n"
            "stp  x4,  x5, [sp,#-16]!   \n"
            "stp  x6,  x7, [sp,#-16]!   \n"
            "stp  x8,  x9, [sp,#-16]!   \n"
            "stp x10, x11, [sp,#-16]!   \n"
            "stp x12, x13, [sp,#-16]!   \n"
            "stp x14, x15, [sp,#-16]!   \n"
            "stp x16, x17, [sp,#-16]!   \n"
            "stp x18, x19, [sp,#-16]!   \n"
            "stp x20, x21, [sp,#-16]!   \n"
            "stp x22, x23, [sp,#-16]!   \n"
            "stp x24, x25, [sp,#-16]!   \n"
            "stp x26, x27, [sp,#-16]!   \n"
            "stp x28, x29, [sp,#-16]!   \n"
            "stp  q0,  q1, [sp,#-16]!   \n"
            "stp  q2,  q3, [sp,#-16]!   \n"
            "stp  q4,  q5, [sp,#-16]!   \n"
            "stp  q6,  q7, [sp,#-16]!   \n"
            "stp  q8,  q9, [sp,#-16]!   \n"
            "stp q10, q11, [sp,#-16]!   \n"
            "stp q12, q13, [sp,#-16]!   \n"
            "stp q14, q15, [sp,#-16]!   \n"
            "stp q16, q17, [sp,#-16]!   \n"
            "stp q18, q19, [sp,#-16]!   \n"
            "stp q20, q21, [sp,#-16]!   \n"
            "stp q22, q23, [sp,#-16]!   \n"
            "stp q24, q25, [sp,#-16]!   \n"
            "stp q26, q27, [sp,#-16]!   \n"
            "stp q28, q29, [sp,#-16]!   \n"
            "stp q30, q31, [sp,#-16]!   \n"
            "mrs x0, nzcv               \n"
            "stp x0, lr, [sp,#-16]!     \n"

            "bl _c_entry                \n"

            "ldp x0, lr, [sp], #16      \n"
            "msr nzcv, x0               \n"
            "ldp q30, q31, [sp], #16    \n"
            "ldp q28, q29, [sp], #16    \n"
            "ldp q26, q27, [sp], #16    \n"
            "ldp q24, q25, [sp], #16    \n"
            "ldp q22, q23, [sp], #16    \n"
            "ldp q20, q21, [sp], #16    \n"
            "ldp q18, q19, [sp], #16    \n"
            "ldp q16, q17, [sp], #16    \n"
            "ldp q14, q15, [sp], #16    \n"
            "ldp q12, q13, [sp], #16    \n"
            "ldp q10, q11, [sp], #16    \n"
            "ldp  q8,  q9, [sp], #16    \n"
            "ldp  q6,  q7, [sp], #16    \n"
            "ldp  q4,  q5, [sp], #16    \n"
            "ldp  q2,  q3, [sp], #16    \n"
            "ldp  q0,  q1, [sp], #16    \n"
            "ldp x28, x29, [sp], #16    \n"
            "ldp x26, x27, [sp], #16    \n"
            "ldp x24, x25, [sp], #16    \n"
            "ldp x22, x23, [sp], #16    \n"
            "ldp x20, x21, [sp], #16    \n"
            "ldp x18, x19, [sp], #16    \n"
            "ldp x16, x17, [sp], #16    \n"
            "ldp x14, x15, [sp], #16    \n"
            "ldp x12, x13, [sp], #16    \n"
            "ldp x10, x11, [sp], #16    \n"
            "ldp  x8,  x9, [sp], #16    \n"
            "ldp  x6,  x7, [sp], #16    \n"
            "ldp  x4,  x5, [sp], #16    \n"
            "ldp  x2,  x3, [sp], #16    \n"
            "ldp  x0,  x1, [sp], #16    \n"
            "br  x18                    \n"
            );
}
