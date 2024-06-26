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
extern fn Java_com_jaeckel_androidportal_MainActivityKt_runTrin(env: JNIEnv, _: JObject) -> jstring {
    android_logger::init_once(
        Config::default().with_max_level(LevelFilter::Trace),
    );

    info!("---------> Running Trin. inside jni-wrapper <--------------");
    let result_future = run_trin(TrinConfig::default());

    let result = executor::block_on(result_future);
    match result {
        Ok(_) => env.new_string("Ok.").unwrap().into_inner(),
        _default => env.new_string("Error.").unwrap().into_inner()
    }
}
