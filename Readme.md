출처 https://github.com/C4-ComeTrue/c4-cometrue-assignment/blob/main/assignments/mini-pay.md

Assignment - Mini-Pay
해당 과제는 총 7개의 Step으로 구성되어 있습니다.
모든 과제는 C4-Cometrue 깃허브 레포에서 관리되어야 하며, 반드시 각 Step이 종료될 때 마다 PR 요청을 날려야 합니다.
물론, 다른 사람들의 PR에 대한 리뷰도 가능합니다.
서버는 필요할 때 편하게 말씀해 주세요.
상당히 고민이 많이 필요한 주제입니다. 일부 Step에 대해서 제공하는 키워드가 힌트가 될 수 있으니, 키워드와 관련한 학습을 진행하는 것을 권장합니다.
궁금증이 있으면 주저하지 말고 서로 커뮤니케이션하고, 그럼에도 해결되지 않는다면 질문을 남겨주세요.
각 PR 마다 설계 의도를 작성해주세요. 좀 더 효율적인 리뷰가 가능할 겁니다.
프로젝트 설명
간단한 형태의 페이 서비스 개발

송금 및 정산 기능을 포함하는 간단한 mini-pay 서버를 개발해 봅시다.
수많은 금전적인 부분을 관리하기 위한 트랜잭션과, 정산을 위한 배치 프로세싱에 대한 이슈를 고민해 봅시다.
Step 1. 계좌 세팅
구현사항
계좌를 생성합시다.
사용자 정보는 평범하게 구현하고, (인증 여부는 하고 싶은대로 하면 됩니다.) 각 사용자가 여러 계좌를 생성할 수 있게 만들어야 합니다.
회원 등록 시, 본인의 "메인 계좌" 가 생성이 됩니다.
이 계좌는 외부 계좌에서 돈을 가져오는 기능이 주 기능이므로, 금액 추가가 가능합니다.
다만, 인당 1일 출전 한도는 3,000,000원 입니다.
추가적으로, "적금 계좌" 를 생성할 수 있습니다.
일단 지금은 이자가 없다고 가정합시다.
이 계좌는 메인 계좌에서 돈을 인출할 수 있으며, 메인 계좌의 돈이 없으면 인출할 수 없습니다.
프로그래밍 요구사항
적금 계좌 - 메인 계좌 간에는 트랜잭션을 신중하게 설계해야 한다.
Transaction Isolation Level을 조사해보고, 어떤 단계를 사용해야 할지 생각해보자.
인당 한도는 어떻게 관리해야 할까?
Step 2. 송금 기능 추가
구현사항
송금 기능을 추가합시다.
친구의 메인 계좌로 송금이 가능합니다.
만약, 잔액이 부족하다면 10,000원 단위로 충전한 후 송금이 이루어진다.
다만, Step 1 에서 언급한 일일 충전 한도는 여기에서도 적용이 됩니다.
인당 한도는 이제 0시 00분을 기점으로 초기화 되어야 합니다.
프로그래밍 요구사항
나의 계좌 - 친구 계좌 간에는 트랜잭션을 신중하게 설계해야 합니다.
이번엔 트랜잭션 범위의 문제이므로. 최대한 트랜잭션을 짧게 가져갈 수 있도록 구상해 봅시다.
많은 사람들이 동시에 한 계좌로 송금하는 시나리오를 설계해 볼까요?. 한 번에 100명이 한 계좌에 송금한다고 가정하면, 이를 어떻게 처리하는게 가장 효울적일까요?
이 이외에도, 해당 시나리오는 생각할 이슈들이 무지막지하게 많습니다! 어떤 것이 언급되지 않았는지 생각해 보세요.
인당 한도의 유효기간이 생각보다 짧아보이는데, 어떻게 관리하는게 좋을까요?
Step 3. 정산 기능 추가
구현사항
정산 기능을 추가합니다.
정산은 사용자가 요청할 수 있으며, 해당하는 사용자들은 확인 후 직접 송금해야 합니다.
정산 방식은 크게 두 가지 입니다.
1/n 정산: 정확히 1/n로 정산을 진행합니다.
랜덤 정산: 전체 금액을 벗어나지 않아야 하며, 랜덤으로 금액을 나눠 정산해야 합니다.
프로그래밍 요구사항
정산 금액의 합은 정확히 맞아야 합니다. 절대 더 적어서도, 더 많아서도 안 됩니다.
20,000원의 금액을 3명이서 1/n 정산해야 한다고 가정해 봅시다. 어떻게 금액을 나눠야 할까요?
Step 4. 송금 기능 구조 변경
구현사항
이제, 송금 시 송금 사용자의 설정에 따라 바로 송금이 이뤄지지 않을수도 있습니다.
이 경우, 금액을 받는 사용자가 직접 확인 후 금액을 받아야 합니다.
우리는 송금을 보냈지만, 아직 수령하지 않은 상황을 "Pending 상태"로 정의합시다.
Pending 상태의 금액은 잔고에서 차감한 것으로 간주합니다. 즉, 잔고가 30,000원인 상태에서 20,000원이 Pending 상태라면, 추가 송금이 가능한 금액은 10,000원 입니다.
Pending 상태의 잔액은 송금을 취소할 경우, 원래 금액으로 수령할 수 있습니다.
Pending 상태로 머물러 있는 최대 기한은 72시간이며, 24시간이 남았을 시 금액을 받는 사용자에게 Remind 알림이 발송됩니다.
프로그래밍 요구사항
Pending 상태의 금액이 절대 유실되어선 안 됩니다. 데이터를 어떤 방식으로 저장해야 할지에 대해서도 매우 깊은 고찰이 필요합니다.
해당 구조 변경이 기존 송금 트랜잭션 구조에 어떠한 영향을 미치나요? 여전히 동시성 문제에서 자유로워야 합니다.
상당한 대량 처리가 필요한 쿼리가 많습니다. 쿼리 성능을 따져가며 기능 구현을 진행해야 합니다.
Step 5. 적금 기능 추가
구현사항
드디어, 적금 계좌의 기능을 강화해 봅시다.
적금은 매일 오전 4시에, 정해진 이자가 들어오는 구조입니다.
상품에 따라, 다음과 같은 기능들이 추가됩니다.
정기 적금: 매일 오전 8시에 가입한 금액 만큼이 자동으로 출금됩니다. 이율은 단리로 5%입니다.
자유 적금: 원할 때 돈을 넣을 수 있습니다. 이율은 단리로 3%입니다.
프로그래밍 요구사항
돈이 없을 시, 어떻게 처리를 해야 할까요?
위쪽 송금 시나리오와 엮어서 고민이 필요합니다. 특히나, 충전을 한 후 적금 계좌에 입금해야 하는 상황이 온다면, 기존 코드를 개선해야 할 수도 있겠네요.
Step 6. 송금 내역 관리
구현사항
돈이 어떻게 들어왔고, 나갔는지를 기록하는 기능을 추가해 봅시다.
각 사용자는 입금이 들어왔을 때 입금 금액의 출처를, 돈이 나갔을 때는 어디로 나갔는지를 확인할 수 있어야 합니다.
최대 1년의 데이터를 저장할 수 있습니다.
이제부터, 송금시에는 이름을 변경할 수 있습니다.
다만, 돈이 나가는 사람이 송금하는 사람 이름이 그대로 기록됩니다.
프로그래밍 요구사항
로그 데이터는 진짜, 진짜, 진짜 엄청나게 빠르게 축적됩니다! 어떻게 해야 효율적으로 관리할 수 있을까요?
ELK를 떠올릴 수 있겠지만, 일단 지금은 잠시 접어둡시다. DB만 사용하여 그나마 효율적으로 처리할 수 있는 방법이 있을지 생각해 봅시다.
데이터가 압도적으로 빠르게 쌓이기 때문에, 쿼리 성능이 더더욱 중요한 이슈가 될 겁니다.
로그와 관련된 API가 무엇이 있을까요? 이에 대응하는 쿼리는 무엇이 있을까요?
Step 7을 위하여, Naive한 쿼리와 튜닝이 진행된 쿼리를 모두 마련해두고, 쿼리 성능을 비교분석 하는 것도 재미 있을 겁니다.
Step 7. 성능 테스트
구현사항
실제로 개발한 서버의 성능이 어떨까요?
사용자의 사용 시나리오를 설계하고, 이를 활용해 스트레스 테스트 툴을 사용한 성능 테스트를 진행해봅시다.
여기서는 nGrinder를 사용해 봅시다.
