#include <mach/mach_types.h>

#ifdef __cplusplus
extern "C" {
#endif
kern_return_t inject_to_task(mach_port_t task, const char *argument);
kern_return_t get_thread_port_for_task(mach_port_t task, mach_port_t *thread);

#ifdef __cplusplus
};
#endif