<template>
    <div class="container" style="font-family: 'Pretendard-Bold'">
        <div class="welcome">
            <div class="pinkbox" :style="{ transform: pinkboxTransform }">
                <div class="signup" :class="{ nodisplay: !showSignup }">
                    <h1>회원가입</h1>
                    <form autocomplete="off" @submit.prevent="formSignUp">
                        <input type="text" placeholder="이메일" v-model="userEmail" />
                        <span
                            v-if="!emailValid"
                            class="error-message animate__animated animate__shakeX"
                            >@를 포함하여 올바른 형식으로 작성해주세요.</span
                        >
                        <input type="password" placeholder="비밀번호" v-model="password1" />
                        <span
                            v-if="!passwordValid"
                            class="error-message animate__animated animate__shakeX"
                            >영문 + 숫자를 포함한 4자이상 20자 이내여야 합니다.</span
                        >
                        <input type="password" placeholder="비밀번호 확인" v-model="password2" />
                        <span
                            v-if="!passwordSamevalid"
                            class="error-message animate__animated animate__shakeX"
                            >비밀번호가 일치하지 않습니다.</span
                        >
                        <button class="button submit">계정 만들기</button>
                    </form>
                </div>
                <div class="signin" :class="{ nodisplay: !showSignin }">
                    <h1>로그인</h1>
                    <form class="more-padding" autocomplete="off" @submit.prevent="formLogIn">
                        <input type="text" placeholder="이메일" v-model="loginEmail" />
                        <span
                            v-if="!loginEmailValid"
                            class="error-message animate__animated animate__shakeX"
                            >이메일을 입력하세요.</span
                        >
                        <input type="password" placeholder="비밀번호" v-model="password" />
                        <span
                            v-if="!loginPasswordValid"
                            class="error-message animate__animated animate__shakeX"
                            >비밀번호를 입력하세요.</span
                        >
                        <button class="button submit">로그인</button>
                    </form>
                    <div class="social-buttons-container">
                        <!-- 카카오 로그인 버튼 -->
                        <button @click="snsLogin('kakao')" class="sns-button">
                            <img src="@/img/kakao.png" alt="Kakao" class="sns-icon" />
                        </button>
                        <!-- 구글 로그인 버튼 -->
                        <button @click="snsLogin('google')" class="sns-button">
                            <img src="@/img/google.png" alt="Google" class="sns-icon" />
                        </button>
                        <!-- 네이버 로그인 버튼 -->
                        <button @click="snsLogin('naver')" class="sns-button">
                            <img src="@/img/naver.png" alt="Naver" class="sns-icon" />
                        </button>
                    </div>
                </div>
            </div>
            <div class="leftbox">
                <h2 class="title"><span>JOB</span>IS</h2>
                <p class="desc">pick your perfect <span>JOB</span></p>
                <!-- <LoginLogo class="logo" /> -->
                <p class="account">계정이 있으신가요?</p>
                <button class="button" @click="showSignIn">로그인</button>
            </div>
            <div class="rightbox">
                <h2 class="title"><span>JOB</span>IS</h2>
                <p class="desc">pick your perfect <span>JOB</span></p>
                <!-- <LoginLogo class="logo" /> -->
                <p class="account">계정이 없으신가요?</p>
                <button class="button" @click="showSignUp">회원가입</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useCounterStore } from '@/stores/counter';
import LoginLogo from '@/components/Users/LoginLogo.vue';
import { watch } from 'vue';
import { signUp, logIn } from '@/apis/api/user';
import router from '@/router';
onMounted(() => {
    // 최상단으로 이동
    window.scrollTo(0, 0);
});

// 회원가입 로직
const store = useCounterStore();
const userEmail = ref('');
const password1 = ref('');
const password2 = ref('');
const emailValid = ref(true);
const passwordValid = ref(true);
const passwordSamevalid = ref(true);

const formSignUp = () => {
    const payload = {
        email: userEmail.value,
        password: password1.value,
    };
    // email 유효성 검사
    const regexEmail = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    emailValid.value = regexEmail.test(userEmail.value);
    // 비밀번호 유효성 검사
    const regexPassword = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{4,20}$/;
    passwordValid.value = regexPassword.test(password1.value);
    passwordSamevalid.value = password1.value === password2.value;
    if (
        // emailValid.value == true &&
        passwordValid.value == true &&
        passwordSamevalid.value == true &&
        emailValid.value == true
    ) {
        // store.signUp(payload);
        if (passwordValid.value == true) {
            if (passwordSamevalid.value == true) {
                signUp(
                    payload,
                    ({ res }) => {
                        // 회원가입 성공
                        console.log(res);
                        alert("회원가입에 성공하였습니다.");
                        showSignIn();
                    },
                    (error) => {
                        // 회원가입 실패
                        console.log(error);
                    }
                );
            }
        }
    }
};

// Email 유효성 검사
// const watchEmail = watch(userEmail, (newEmail, oldEmail) => {
//     const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
//     emailValid.value = regex.test(newEmail);
// });

// 비밀번호 유효성 검사
// const watchPassword = watch([password1, password2], ([pass1, pass2]) => {
//     const regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{4,20}$/;
//     passwordValid.value = regex.test(pass1);
//     passwordSamevalid.value = pass1 === pass2;
// });

// 로그인 로직
const loginEmail = ref('');
const password = ref('');
const loginEmailValid = ref(true);
const loginPasswordValid = ref(true);
const login = store.isLogin;

const formLogIn = () => {
    const payload = {
        email: loginEmail.value,
        password: password.value,
    };
    // email 유효성 검사
    const regexEmail = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    loginEmailValid.value = regexEmail.test(loginEmail.value);
    // 비밀번호 유효성 검사
    const regexPassword = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{4,20}$/;
    loginPasswordValid.value = regexPassword.test(password.value);
    if (loginEmailValid.value == true) {
        // store.signUp(payload);
        if (loginPasswordValid.value == true) {
            logIn(payload, (res) => {
                // 로그인 성공
                const accessToken = res.headers.get('authorization');
                const refreshToken = res.headers.get('refresh-token');
                // console.log(res.headers);
                // console.log(refreshToken);

                localStorage.setItem('accessToken', accessToken.replace('Bearer ', ''));
                localStorage.setItem('refreshToken', refreshToken);
                store.setIsLogin(true);

                alert('로그인에 성공하였습니다!');

                router.push({ name: 'home' });
            }),
                (error) => {
                    console.log(error);
                    alert("아이디 혹은 비밀번호가 일치하지 않습니다.")
                };
        }
    }
};

// 로그인 회원가입 화면 전환 로직
const showSignup = ref(false);
const showSignin = ref(true);

const pinkboxTransform = computed(() => {
    return showSignup.value ? 'translateX(80%)' : 'translateX(0%)';
});

const showSignIn = () => {
    showSignup.value = false;
    showSignin.value = true;
};

const showSignUp = () => {
    showSignup.value = true;
    showSignin.value = false;
};

// 소셜로그인
const snsLogin = (type) => {
    const redirect_uri = window.location.origin + '';
    console.log(redirect_uri);
    window.location.href = `${import.meta.env.VITE_APP_API_URL}/user/login/${type}?redirect_uri=${redirect_uri}`;
};
</script>

<style lang="scss" scoped>
@import url('@/assets/login.scss');
</style>
