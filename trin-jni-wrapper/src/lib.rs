use jni::JNIEnv;
use jni::objects::{JObject};
use jni::sys::jstring;
use futures::executor;
use trin::run_trin;
use ethportal_api::types::cli::{TrinConfig, Web3TransportType};
use log::{info, LevelFilter};
use android_logger::Config;
use tracing::log::debug;
extern crate android_logger;
use std::panic;

#[no_mangle]
extern fn Java_com_jaeckel_androidportal_MainActivityKt_runTrin(env: JNIEnv<'_>, _: JObject<'_>) -> jstring {
    android_logger::init_once(
        Config::default().with_max_level(LevelFilter::Trace),
    );
    panic::set_hook(Box::new(|pi| {
        info!("{pi}");
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
            config.no_stun = false;
            config.no_upnp = false;
            config.discovery_port = 9009;
            config.web3_transport = Web3TransportType::HTTP;

            info!("Starting trin... ");
            let result_future = run_trin(config);
            let result = executor::block_on(result_future);
            match result {
                Ok(_) => info!("--> Result Ok()."),
                Err(e) => info!("--> Error: {}", e),
            }

            info!("...Done.");
        });
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
