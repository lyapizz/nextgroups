(this["webpackJsonpnextgroups-frontend-react"]=this["webpackJsonpnextgroups-frontend-react"]||[]).push([[0],{12:function(e,t,n){},13:function(e,t,n){},15:function(e,t,n){"use strict";n.r(t);var c=n(1),a=n.n(c),r=n(3),o=n.n(r),s=(n(12),n(4)),l=n(5),i=n(7),u=n(6),d=(n(13),n(0)),p=function(e){var t=e.teams;return Object(d.jsx)("tbody",{children:t.map((function(e){return Object(d.jsx)("tr",{children:Object(d.jsx)("td",{children:Object(d.jsxs)("a",{href:e.teamPage,children:[" ",e.player1," & ",e.player2]})})})}))})},b=function(e){var t=e.groups;return Object(d.jsx)("div",{children:t.map((function(e){return Object(d.jsxs)("table",{className:"table table-striped table-hover",children:[Object(d.jsxs)("th",{align:"left",children:["Group ",e.id]}),Object(d.jsx)(p,{teams:e.teams})]})}))})},j=function(e){var t=e.groupResults;return Object(d.jsx)("div",{className:"accordion accordion-flush",id:"accordionExample",children:t.map((function(e,t){return Object(d.jsxs)("div",{className:"accordion-item",id:"item_"+t,children:[Object(d.jsx)("h3",{className:"accordion-header",id:"heading_"+t,children:Object(d.jsxs)("button",{className:"accordion-button collapsed",type:"button","data-bs-toggle":"collapse","aria-expanded":"true","data-bs-target":"#collapse_"+t,"aria-controls":"collapse_"+t,children:["Category: ",e.category]})}),Object(d.jsx)("div",{id:"collapse_"+t,className:"accordion-collapse collapse show","data-bs-parent":"#accordionExample","aria-labelledby":"heading_"+t,children:Object(d.jsxs)("div",{className:"accordion-body",id:"groups-body_"+t,children:[null!=e.problem&&Object(d.jsx)("div",{className:"alert alert-warning",role:"alert",children:e.problem}),Object(d.jsx)(b,{groups:e.groups})]})})]})}))})},h=function(e){Object(i.a)(n,e);var t=Object(u.a)(n);function n(){var e;Object(s.a)(this,n);for(var c=arguments.length,a=new Array(c),r=0;r<c;r++)a[r]=arguments[r];return(e=t.call.apply(t,[this].concat(a))).state={groupResults:[]},e}return Object(l.a)(n,[{key:"componentDidMount",value:function(){var e=this;fetch("https://cutshot-next-groups.herokuapp.com/api/nextGroups").then((function(e){return e.json()})).then((function(t){e.setState({groupResults:t})})).catch(console.log)}},{key:"render",value:function(){return Object(d.jsx)(j,{groupResults:this.state.groupResults})}}]),n}(c.Component),g=function(e){e&&e instanceof Function&&n.e(3).then(n.bind(null,16)).then((function(t){var n=t.getCLS,c=t.getFID,a=t.getFCP,r=t.getLCP,o=t.getTTFB;n(e),c(e),a(e),r(e),o(e)}))};o.a.render(Object(d.jsx)(a.a.StrictMode,{children:Object(d.jsx)(h,{})}),document.getElementById("root")),g()}},[[15,1,2]]]);
//# sourceMappingURL=main.f2d299f1.chunk.js.map