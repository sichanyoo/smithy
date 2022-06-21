namespace example

use aws.protocols#restJson1

@restJson1
service Example {
    version: "0000-00-00",
    operations: [Foo, Bar]
}

@idempotent
@http(method: "PUT", uri: "/test/{fooLabel}", code: 200)
operation Foo {
    input: FooInput,
    output: FooOutput,
    errors: [FooBarError, FooBarError2]
}

@idempotent
@http(method: "PATCH", uri: "/test/get", code: 200)
operation Bar {
    input: BarInput,
    output: BarOutput,
    errors: [FooBarError]
}

@input
structure FooInput {
    @httpHeader("fooStringInputHeader")
    fooStringHeader: String,

    @required
    @httpLabel
    fooLabel: String,

    @httpQuery("queryParam")
    fooParam: exampleList,

    fooBody1: String,
    fooBody2: String
}

@input
structure BarInput {
    @httpHeader("barListInputHeader")
    barListHeader: exampleList,

    @httpHeader("barStringInputHeader")
    barStringHeader: String,

    @httpQueryParams()
    barQueryParams: exampleMap,

    @httpPayload
    barPayload: String
}

list exampleList {
    member: String
}

map exampleMap {
    key: String,
    value: String
}

@output
structure FooOutput {
    @httpHeader("fooStringOutputHeader")
    fooOutputStringHeader: String,

    @httpHeader("fooListOutputHeader")
    fooOutputListHeader: exampleList,

    fooOutputBody1: String,
    fooOutputBody2: String
}

@output
structure BarOutput {
    @httpHeader("fooBarStringOutputHeader")
    barOutputStringHeader: String,

    @httpHeader("fooBarListOutputHeader")
    barOutputListHeader: exampleList,

    barOutputBody1: String,
    barOutputBody2: String
}

@error("client")
structure FooBarError {
    @httpHeader("Z-Foo")
    errorHeader: String,

    @httpPayload
    message: String
}

@error("server")
structure FooBarError2 {
    message1: String,
    message2: String,
    message3: String
}

apply Foo @examples(
  [
      {
           title: "Foo valid example",
           documentation: "fooTestDoc",
           input: {
                fooStringHeader: "abc",
                fooLabel: "def",
                fooParam: ["a", "b", "c"],
                fooBody1: "fooValidBody1",
                fooBody2: "fooValidBody2"
           },
           output: {
                fooOutputStringHeader: "fooValidHeader",
                fooOutputListHeader: ["fooHeader1", "fooHeader2", "fooHeader3"],
                fooOutputBody1: "fooBarValidBody1",
                fooOutputBody2: "fooBarValidBody2"
           },
      },

      {
           title: "Foo error example",
           documentation: "fooTestDoc2",
           input: {
                fooStringHeader: "hij",
                fooLabel: "klm",
                fooParam: ["x", "y", "z"],
                fooBody1: "fooValidBody3",
                fooBody2: "fooValidBody4"
           },
           error: {
                shapeId: FooBarError,
                content: {
                    errorHeader: "fooBarErrorHeader1",
                    message: "fooBarError1"
                }
           },
      },

      {
           title: "Foo error example 2",
           documentation: "fooTestDoc3",
           input: {
                fooStringHeader: "nop",
                fooLabel: "qrs",
                fooParam: ["@", "#", "$"],
                fooBody1: "fooValidBody5",
                fooBody2: "fooValidBody6"
           },
           error: {
                shapeId: FooBarError2,
                content: {
                    message1: "fooBarError2message1",
                    message2: "fooBarError2message2",
                    message3: "fooBarError2message3"
                }
           },
      }
  ]
)

apply Bar @examples(
  [
      {
           title: "Bar valid example",
           documentation: "barTestDoc",
           input: {
                barListHeader: ["1", "2", "3"],
                barStringHeader: "barStringHeader1",
                barQueryParams: {"query1" : "sea", "query2" : "hill"},
                barPayload: "barPayload1"
           },
           output: {
                barOutputStringHeader: "barValidHeader",
                barOutputListHeader: ["barHeader1", "barHeader2", "barHeader3"],
                barOutputBody1: "fooBarValidBody3",
                barOutputBody2: "fooBarValidBody4"
           },
      },

      {
           title: "Bar error example",
           documentation: "barTestDoc2",
           input: {
                barListHeader: ["7", "8", "9"],
                barStringHeader: "barStringHeader2",
                barQueryParams: {"query1" : "cold", "query2" : "warm"},
                barPayload: "barPayload2"
           },
           error: {
                shapeId: FooBarError,
                content: {
                    errorHeader: "fooBarErrorHeader2",
                    message: "fooBarError2"
                }
           },
      }
  ]
)