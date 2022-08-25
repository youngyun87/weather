import whetherComponent from './components/whetherComponent.js'; // 컴포넌트1 import


//각 컴포넌트들을 변수에 할당
const whether_component = whetherComponent;


//뷰인스턴스 생성 및 컴포넌트들 저장
new Vue({
	el : '#app',
	components: {
		'whether-component': whether_component
	}
})