use jni::JNIEnv;
use jni::objects::{JObject};
use jni::sys::jstring;
use futures::executor;
use trin::run_trin;
use ethportal_api::types::cli::TrinConfig;
use log::{info, LevelFilter};
use android_logger::Config;
use tracing::log::debug;
extern crate android_logger;

#[no_mangle]
extern fn Java_com_jaeckel_androidportal_MainActivityKt_runTrin(env: JNIEnv<'_>, _: JObject<'_>) -> jstring {
    android_logger::init_once(
        Config::default().with_max_level(LevelFilter::Trace),
    );
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

use std::future::Future;
use tokio::runtime::Runtime;
use tokio::task::JoinHandle;
use tokio::net::UdpSocket;

static mut RUNTIME: Option<Runtime> = None;

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
            config.no_stun = true;
            config.no_upnp = true;
            config.discovery_port = 9091;
            //config.external_addr = "127.0.0.1";

            let sock = UdpSocket::bind("0.0.0.0:9092").await;
            debug!("Socket:  {:?}", sock);

            let sock = UdpSocket::bind("0.0.0.0:9092").await;
            debug!("Socket:  {:?}", sock);

            info!("Starting ... ");
            let result_future = run_trin(config);
            let result = executor::block_on(result_future);
            info!("...Done.");

        })


//     unsafe {
//         RUNTIME.replace(
//             tokio::runtime::Builder::new_multi_thread()
//                 .threaded_scheduler()
//                 .core_threads(runtime_core_threads_count())
//                 .on_thread_start(|| {
//                     info!("Tokio worker thread started: {:?}", std::thread::current().id());
//
//                     info!(
//                         "Attaching Tokio worker thread with JVM: {:?}",
//                         std::thread::current().id()
//                     );
//                     //crate::bridge::jvm().attach_thread();
//                 })
//                 .on_thread_stop(|| {
//                     info!("Tokio worker thread stopped: {:?}", std::thread::current().id());
//                 })
//                 .enable_io()
//                 .enable_time()
//                 .build()
//                 .unwrap(),
//         );
//     }
}
/// Calls whenever the runtime is shut down.
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
