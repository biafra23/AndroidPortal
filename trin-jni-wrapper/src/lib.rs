use std::{env, panic};
use std::path::Path;
use jni::JNIEnv;
use jni::objects::{JObject};
use jni::sys::jstring;
use futures::executor;
use trin::run_trin;
use ethportal_api::types::cli::{TrinConfig, Web3TransportType};
use log::{error, info, LevelFilter};
use android_logger::Config;
//use tracing::log::info;
extern crate android_logger;
use tokio::runtime::Runtime;
use tokio::signal;

//use std::{io, panic, stdout, stderr};
static mut RUNTIME: Option<Runtime> = None;

#[no_mangle]
extern fn Java_com_jaeckel_androidportal_MainActivityKt_runTrin(env: JNIEnv<'_>, _: JObject<'_>) -> jstring {
    android_logger::init_once(
        Config::default().with_max_level(LevelFilter::Trace),
    );
    panic::set_hook(Box::new(|panic_info| {
        error!("{panic_info}");
    }));

    info!("start_runtime");
    start_runtime();


    //     let result_future = run_trin(config);
    //     let result = executor::block_on(result_future);
    //     match result {
    //         Ok(_) => env.new_string("Ok.").unwrap().into_inner(),
    //         _default => env.new_string("Error.").unwrap().into_inner()
    //     }
    env.new_string("Ok.").unwrap().into_inner()
}


/// Calls whenever the runtime is started or re-started.
pub fn start_runtime() {
    info!(
        "Starting tokio runtime from thread id: {:?}, System: Logical cores: {}, Physical cores: {}, Tokio Runtime core threads: {}",
        std::thread::current().id(),
        num_cpus::get(),
        num_cpus::get_physical(),
        runtime_core_threads_count()
    );

    tokio::runtime::Builder::new_multi_thread()
        .enable_all()
        .build()
        .unwrap()
        .block_on(async {
            let mut config = TrinConfig::default();
            config.no_stun = false;
            config.no_upnp = true;
            config.discovery_port = 9009;
            config.web3_transport = Web3TransportType::HTTP;

            info!("Starting trin... ");
            let result_future = run_trin(config);

            let result = executor::block_on(result_future);
            info!("Waiting for ctrl-c :-/");
            signal::ctrl_c().await.expect("failed to listen for event");

            match result {
                Ok(_) => info!("===> Result Ok()."),
                Err(e) => info!("===> Error: {}", e),
            }
        });
}
// Called whenever the runtime is shut down.
pub fn shutdown_runtime() {
    if let Some(runtime) = unsafe { RUNTIME.take() } {
        info!("Shutdown tokio runtime started");
        runtime.shutdown_timeout(tokio::time::Duration::from_secs(1));
        info!("Shutdown tokio runtime done");
    }
}

fn runtime_core_threads_count() -> usize {
    num_cpus::get().min(4).max(2)
}

// use std::ffi::CString;
// use std::os::raw::{c_char, c_int};
// use std::ptr;
// use std::thread;
// use libc::setvbuf;
//
// static mut PFD: [c_int; 2] = [0; 2];
// static mut THR: Option<thread::JoinHandle<()>> = None;
// //static mut TAG: *const c_char = b"com.jaeckel.androidportal\0" as *const u8 as *const c_char;
//
// fn start_logger(app_name: &str) -> Result<(), i32> {
//     unsafe {
//         let TAG = CString::new(app_name).unwrap().into_raw();
//
//         // Make stdout line-buffered and stderr unbuffered
//         setvbuf(io::stdout, ptr::null_mut(), libc::_IOLBF, 0);
//         setvbuf(io::Stderr, ptr::null_mut(), libc::_IONBF, 0);
//
//         // Create the pipe and redirect stdout and stderr
//         if libc::pipe(PFD.as_mut_ptr()) != 0 {
//             return Err(-1);
//         }
//         libc::dup2(PFD[1], 1);
//         libc::dup2(PFD[1], 2);
//
//         // Spawn the logging thread
//         THR = Some(thread::spawn(thread_func));
//     }
//     Ok(())
// }
//
// unsafe fn thread_func() {
//     let mut buf = [0u8; 128];
//     loop {
//         let rdsz = libc::read(PFD[0], buf.as_mut_ptr() as *mut libc::c_void, buf.len());
//         if rdsz > 0 {
//             if buf[rdsz as usize - 1] == b'\n' {
//                 buf[rdsz as usize - 1] = 0;
//             }
//             let log_msg = CString::from_raw(TAG).to_str().unwrap();
//             eprintln!("{}: {}", log_msg, String::from_utf8_lossy(&buf[..rdsz as usize]));
//         } else {
//             break;
//         }
//     }
// }
